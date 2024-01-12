package com.knowroaming.esim.app.presentation.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.domain.service.AuthState
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.input.AppTextField
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.components.main.AppTextButton
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.ext.isValidEmail
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
) {
    val isEmailValid
        get(): Boolean {
            if (email.isEmpty()) return true
            return email.isValidEmail()
        }

    val isValid
        get(): Boolean {
            return email.isValidEmail() && password.isNotEmpty()
        }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(navigator: Navigator) {

    val viewModel = koinViewModel(vmClass = AuthViewModel::class)

    val listState = rememberLazyListState()
    val (state, setState) = remember { mutableStateOf(LoginScreenState()) }
    val snackbar = koinInject<SnackbarHostState>()

    val auth by viewModel.auth.collectAsState(null)
    val error by viewModel.error.collectAsState(null)
    val isLoading by viewModel.loading.collectAsState(false)

    LaunchedEffect(auth) {
        when (auth) {
            is AuthState.Authenticated -> {
                snackbar.showSnackbar("Login Successful")
                navigator.goBackWith((auth as AuthState.Authenticated).user)
            }

            else -> {}
        }
    }

    LaunchedEffect(error) {
        error?.message?.let {
            snackbar.showSnackbar(it)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(BrandSize.lg, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            start = BrandSize.lg, end = BrandSize.lg, bottom = BrandSize.x2l
        )
    ) {
        item {
            Image(
                contentDescription = "Logo",
                painter = painterResource("icon.png"),
                modifier = Modifier.padding(BrandSize.md).width(398.dp).height(128.dp),
            )
        }

        item {
            AppTextField(
                placeholder = "Email Address",
                value = state.email,
                action = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Email),
                        contentDescription = "Email Icon",
                    )
                },
                isError = !state.isEmailValid,
                supportingText = if (!state.isEmailValid) {
                    {
                        Text("Please provide a valid email")
                    }
                } else null,
            ) {
                setState(state.copy(email = it))
            }
        }

        item {
            AppTextField(
                placeholder = "Password",
                value = state.password,
                action = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.login(state.email, state.password)
                }),
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Lock),
                        contentDescription = "Email Icon",
                    )
                },
            ) {
                setState(state.copy(password = it))
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
                AppDivider()
                AppButton(
                    text = "Login",
                    loading = isLoading,
                    enabled = state.isValid,
                    type = AppButtonVariant.Primary,
                    onClick = {
                        viewModel.login(state.email, state.password)
                    },
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(
                        BrandSize.md, Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 16.sp,
                            lineHeight = 19.2.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    AppTextButton(
                        text = "Sign Up",
                        onClick = {
                            navigator.goBack()
                            navigator.navigate(
                                AppRoute.SIGN_UP, options = NavOptions(
                                    launchSingleTop = true
                                )
                            )
                        },
                    )
                }

                AppTextButton(
                    text = "Terms & Conditions",
                    onClick = {
                        navigator.goBack()
                        navigator.navigate(
                            AppRoute.TERMS_AND_CONDs, options = NavOptions(
                                launchSingleTop = true
                            )
                        )
                    },
                )
            }
        }
    }
}