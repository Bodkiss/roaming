package com.knowroaming.esim.app.util.injection

import androidx.compose.material3.SnackbarHostState
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.domain.network.AuthAPI
import com.knowroaming.esim.app.domain.network.CountryAPI
import com.knowroaming.esim.app.domain.network.ESimApi
import com.knowroaming.esim.app.domain.repository.AuthProvider
import com.knowroaming.esim.app.domain.repository.CountryRepository
import com.knowroaming.esim.app.domain.repository.ESimRepository
import com.knowroaming.esim.app.domain.service.AuthService
import com.knowroaming.esim.app.domain.service.AuthState
import com.knowroaming.esim.app.domain.service.CountryService
import com.knowroaming.esim.app.domain.service.ESimService
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.presentation.model.CountryListViewModel
import com.knowroaming.esim.app.presentation.model.ESimListViewModel
import com.knowroaming.esim.app.presentation.model.PackageListViewModel
import com.knowroaming.esim.app.presentation.util.search.AppSearch
import com.knowroaming.esim.app.presentation.util.search.DestinationSearch
import com.knowroaming.esim.app.presentation.util.search.TopicSearch
import com.knowroaming.esim.app.util.AuthPlugin
import com.knowroaming.esim.app.util.BuildConfig
import com.knowroaming.esim.app.util.converter.ResponseConverterFactory
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


expect val platformModule: Module

private fun buildKtorClient(): Ktorfit {
    return ktorfit {
        baseUrl(BuildConfig.API_URL)
        httpClient(HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(AuthPlugin) {
                token = BuildConfig.API_KEY
            }
        })
        converterFactories(
            FlowConverterFactory(),
            CallConverterFactory(),
            ResponseConverterFactory(),
        )
    }
}

val commonModule = module {
    single { SnackbarHostState() }
    single<AuthAPI> { buildKtorClient().create() }
    single<ESimApi> { buildKtorClient().create() }
    single<CountryAPI> { buildKtorClient().create() }
    single<AuthProvider> { AuthService(api = get()) }
    single<ESimRepository> { ESimService(api = get()) }
    single<CountryRepository> { CountryService(api = get()) }

    single { get<AuthProvider>().auth }
    single<Flow<Customer?>>(named(AppKoin.AUTH_USER)) {
        get<Flow<AuthState>>().map {
            when (it) {
                is AuthState.Registered -> it.user
                is AuthState.Authenticated -> it.user
                is AuthState.Updated -> it.user
                AuthState.Unauthenticated -> null
            }
        }
    }

    single<Flow<Boolean>>(named(AppKoin.AUTHENTICATED)) {
        get<Flow<AuthState>>().map { it is AuthState.Authenticated }
    }

    factory { AuthViewModel(get()) }
    factory { ESimListViewModel(get()) }
    factory { CountryListViewModel(get()) }
    factory { PackageListViewModel(get()) }
    factory<AppSearch>(named(AppKoin.COUNTRY_SEARCH)) { DestinationSearch(get()) }
    factory<AppSearch>(named(AppKoin.TOPIC_SEARCH)) { TopicSearch() }
}

fun appModule() = listOf(commonModule, platformModule)

fun initKoin() = startKoin {
    modules(appModule())
}

enum class AppKoin {
    AUTH_USER, COUNTRY_SEARCH, AUTHENTICATED, TOPIC_SEARCH
}

