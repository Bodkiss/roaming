import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.knowroaming.esim.app.presentation.components.main.AppBottomNavigationBar
import com.knowroaming.esim.app.presentation.components.main.AppNavigationBar
import com.knowroaming.esim.app.presentation.components.main.AppScaffold
import com.knowroaming.esim.app.presentation.components.main.AppWebViewScreen
import com.knowroaming.esim.app.presentation.model.ESimListViewModel
import com.knowroaming.esim.app.presentation.theme.AppTheme
import com.knowroaming.esim.app.presentation.util.menu.DestinationMenu
import com.knowroaming.esim.app.presentation.util.menu.MainMenu
import com.knowroaming.esim.app.presentation.util.menu.PurchaseMenu
import com.knowroaming.esim.app.presentation.util.search.AppSearch
import com.knowroaming.esim.app.presentation.view.MenuScreen
import com.knowroaming.esim.app.presentation.view.auth.LoginScreen
import com.knowroaming.esim.app.presentation.view.auth.SignUpScreen
import com.knowroaming.esim.app.presentation.view.auth.VerifyEmailScreen
import com.knowroaming.esim.app.presentation.view.esim.ESimDetailScreen
import com.knowroaming.esim.app.presentation.view.esim.TopUpESimScreen
import com.knowroaming.esim.app.presentation.view.extra.AboutScreen
import com.knowroaming.esim.app.presentation.view.extra.SupportScreen
import com.knowroaming.esim.app.presentation.view.profile.ProfileScreen
import com.knowroaming.esim.app.presentation.view.profile.UpdateProfileScreen
import com.knowroaming.esim.app.presentation.view.purchase.AutomaticInstallationScreen
import com.knowroaming.esim.app.presentation.view.purchase.ManualInstallationScreen
import com.knowroaming.esim.app.presentation.view.purchase.PurchaseESimScreen
import com.knowroaming.esim.app.presentation.view.purchase.QRCodeScreen
import com.knowroaming.esim.app.presentation.view.store.PopularScreen
import com.knowroaming.esim.app.presentation.view.store.destinations.CountriesScreen
import com.knowroaming.esim.app.presentation.view.store.destinations.PackagesScreen
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.generateImageLoader
import com.knowroaming.esim.app.util.injection.AppKoin
import com.seiko.imageloader.LocalImageLoader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.qualifier.named


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun App() {
    CompositionLocalProvider(LocalImageLoader provides generateImageLoader()) {
        PreComposeApp {
            KoinContext {
                AppTheme {

                    val countrySearch = koinInject<AppSearch>(
                        named(AppKoin.COUNTRY_SEARCH)
                    )

                    val supportSearch = koinInject<AppSearch>(
                        named(AppKoin.TOPIC_SEARCH)
                    )

                    val navigator = rememberNavigator()

                    val current by navigator.currentEntry.collectAsState(null)

                    val route = current?.route?.route ?: ""

                    val authenticated by koinInject<Flow<Boolean>>(named(AppKoin.AUTHENTICATED)).collectAsState(
                        false
                    )

                    val (title, setTitle) = remember { mutableStateOf("") }

                    val search = when (true) {
                        route.contains(AppRoute.FAQ) -> supportSearch
                        route.contains(AppRoute.STORE) -> countrySearch
                        route.contains(AppRoute.PRODUCT) -> null
                        else -> null
                    }

                    val menu = when (true) {
                        route.contains(AppRoute.DESTINATIONS) -> DestinationMenu
                        route.contains(AppRoute.DESTINATION_PRODUCT_PURCHASED) -> PurchaseMenu(
                            current?.query<String>("order_uuid")
                        )

                        else -> null
                    }

                    val eSimListViewModel = koinInject<ESimListViewModel>()

                    AppScaffold(topBar = {
                        AppNavigationBar(
                            title, navigator = navigator, search = search, menu = menu
                        )
                    }, bottomBar = {
                        val isSearchOpen = search?.state?.active ?: false

                        if (!isSearchOpen) {
                            AppBottomNavigationBar(navigator)
                        }
                    }) { padding ->

                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(padding)
                        ) {
                            NavHost(
                                navigator = navigator,
                                initialRoute = AppRoute.STORE,
                            ) {

                                dialog(AppRoute.MENU) {
                                    MenuScreen(
                                        menu = MainMenu(authenticated),
                                        navigator = navigator,
                                    )
                                }

                                group(AppRoute.AUTH, initialRoute = AppRoute.LOGIN) {
                                    dialog(AppRoute.LOGIN) {
                                        LoginScreen(navigator)
                                    }

                                    dialog(AppRoute.SIGN_UP) {
                                        SignUpScreen(navigator)
                                    }

                                    dialog(AppRoute.SIGN_UP_OTP) {
                                        VerifyEmailScreen(navigator)
                                    }
                                }

                                scene(AppRoute.ABOUT) {
                                    setTitle("About")
                                    AboutScreen(navigator)
                                }

                                scene(AppRoute.SUPPORT) {
                                    setTitle("Support")
                                    SupportScreen(navigator)
                                }

                                scene(AppRoute.FAQ) {
                                    setTitle("FAQ")
                                    AppWebViewScreen("https://knowroaming.vercel.app/support")
                                }

                                scene(AppRoute.PRIVACY_POLICY) {
                                    setTitle("Privacy Policy")
                                    AppWebViewScreen("https://knowroaming.vercel.app/privacy")
                                }

                                scene(AppRoute.TERMS_AND_CONDs) {
                                    setTitle("Terms & Conditions")
                                    AppWebViewScreen("https://knowroaming.vercel.app/terms-conditions")
                                }

                                group(AppRoute.STORE, initialRoute = AppRoute.POPULAR) {
                                    scene(AppRoute.POPULAR) {
                                        setTitle("Know Roaming")
                                        PopularScreen(navigator)
                                    }

                                    group(
                                        AppRoute.DESTINATIONS,
                                        initialRoute = AppRoute.DESTINATIONS_All
                                    ) {
                                        scene(AppRoute.DESTINATION) { backstack ->
                                            PackagesScreen(
                                                backstack.path<String?>("code"),
                                                navigator = navigator
                                            ) {
                                                setTitle(it.name)
                                            }
                                        }

                                        scene(AppRoute.DESTINATIONS_All) {
                                            setTitle("Store")
                                            CountriesScreen(navigator)
                                        }

                                        scene(AppRoute.DESTINATIONS_REGIONS) {
                                            setTitle("Store")
                                            CountriesScreen(navigator, true)
                                        }
                                    }


                                    scene(AppRoute.DESTINATION_PRODUCT_DETAILS) { backstack ->
                                        setTitle("Purchase eSIM")
                                        PurchaseESimScreen(
                                            navigator = navigator,
                                            iccid = backstack.query("iccid"),
                                            countryCode = backstack.path<String>("code") as String,
                                            packageTypeId = backstack.path<Int>("package") as Int
                                        )
                                    }

                                    scene(AppRoute.DESTINATION_PRODUCT_PURCHASED) { backstack ->
                                        val orderId = remember {
                                            backstack.query<String>(
                                                "order_uuid", ""
                                            ) as String
                                        }

                                        eSimListViewModel.getOrderById(orderId)

                                        val orderState by eSimListViewModel.order

                                        when (backstack.query("method", "auto")) {
                                            "qr_code" -> QRCodeScreen(navigator, orderState)
                                            "manual" -> ManualInstallationScreen(
                                                navigator, orderState
                                            )

                                            else -> AutomaticInstallationScreen(
                                                navigator, orderState
                                            )
                                        }
                                    }
                                }
                                group(AppRoute.MY_ESIMS, initialRoute = AppRoute.ESIM_LIST) {
                                    scene(AppRoute.ESIM_LIST) {
                                        setTitle("My eSims")
                                        EsimListScreen(
                                            navigator = navigator
                                        )
                                    }
                                    scene(AppRoute.ESIM_DETAILS) { backstack ->
                                        setTitle("eSIM Details")

                                        backstack.path<String>("iccid")?.let {
                                            ESimDetailScreen(
                                                it, navigator
                                            )
                                        }
                                    }
                                    scene(AppRoute.ESIM_TOP_UP) { backstack ->
                                        setTitle("Top Up eSIM")
                                        TopUpESimScreen(
                                            navigator = navigator,
                                            iccid = backstack.path<String>("iccid") as String,
                                            countryCode = backstack.query<String>("country_code") as String
                                        )
                                    }
                                }
                                group(
                                    AppRoute.PROFILE, initialRoute = AppRoute.PROFILE_DETAILS
                                ) {
                                    scene(AppRoute.PROFILE_DETAILS) {
                                        setTitle("My Profile")
                                        ProfileScreen(navigator)
                                    }
                                    scene(AppRoute.PROFILE_UPDATE) {
                                        setTitle("Update Profile")
                                        UpdateProfileScreen()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}