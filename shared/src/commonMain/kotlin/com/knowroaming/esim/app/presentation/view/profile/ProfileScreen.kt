package com.knowroaming.esim.app.presentation.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(navigator: Navigator) {
    val viewModel = koinViewModel(vmClass = AuthViewModel::class)

    val listState = rememberLazyListState()

    val snackbar = koinInject<SnackbarHostState>()

    val customer by koinInject<Flow<Customer?>>(named(AppKoin.AUTH_USER)).collectAsState(null)

    val authenticated = customer != null

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(BrandSize.lg, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(BrandSize.lg),
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BrandSize.lg)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(128.dp).clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource("profile.xml"),
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                customer?.fullName?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.displaySmall
                    )
                }
            }

        }

        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    BrandSize.lg, Alignment.CenterVertically
                ),
            ) {

                if (!authenticated) {
                    AppButton(type = AppButtonVariant.Primary, text = "Log In", onClick = {
                        navigator.navigate(AppRoute.LOGIN)
                    })
                    AppButton(type = AppButtonVariant.Primary, text = "Sign Up", onClick = {
                        navigator.navigate(
                            AppRoute.SIGN_UP, options = NavOptions(
                                launchSingleTop = true
                            )
                        )
                    })
                }

                if (authenticated) {
                    AppButton(type = AppButtonVariant.Primary, text = "Update Profile", onClick = {
                        navigator.navigate(AppRoute.PROFILE_UPDATE)
                    })
                    AppDivider()
                    AppButton(type = AppButtonVariant.Tertiary, text = "Sign Out", onClick = {
                        viewModel.logout()

                        CoroutineScope(Dispatchers.IO).launch {
                            snackbar.showSnackbar("You have be signed out!")
                        }
                    })
                }
            }
        }
    }
}