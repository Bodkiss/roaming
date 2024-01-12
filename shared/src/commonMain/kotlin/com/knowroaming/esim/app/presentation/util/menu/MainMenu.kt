package com.knowroaming.esim.app.presentation.util.menu

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.util.AppRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.query
import org.koin.compose.koinInject

data class MainMenu(val authenticated: Boolean) : AppMenu {

    override val items: List<AppMenu.Item>
        get() = listOf(
            AppMenu.Item("Store", AppRoute.STORE),
            AppMenu.Item("My eSIMs", AppRoute.MY_ESIMS),
            AppMenu.Item("Profile", AppRoute.PROFILE),
            AppMenu.Item("Support", AppRoute.SUPPORT),
            AppMenu.Item("Log In", AppRoute.LOGIN),
            AppMenu.Item("Sign Up", AppRoute.SIGN_UP),
            AppMenu.Item("About", AppRoute.ABOUT),
        ).also { links ->
            return if (authenticated) {
                links.filter { !it.route.contains(AppRoute.AUTH) }
                    .plus(AppMenu.Item("Sign Out", AppRoute.SIGN_OUT) { signOut() })
            } else links
        }

    override fun selected(backstack: BackStackEntry, item: AppMenu.Item): Boolean {
        return backstack.query("active", "")?.contains(item.route) == true
    }


    @Composable
    fun signOut() {
        val snackbar = koinInject<SnackbarHostState>()
        val viewModel = koinViewModel(vmClass = AuthViewModel::class)

        viewModel.logout()

        CoroutineScope(Dispatchers.IO).launch {
            snackbar.showSnackbar("You have be signed out!")
        }
    }
}