package com.knowroaming.esim.app.presentation.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.knowroaming.esim.app.util.ext.isInternationalPhoneNumber
import com.knowroaming.esim.app.util.ext.isPhoneNumberChar
import com.knowroaming.esim.app.util.ext.isStrongPassword
import com.knowroaming.esim.app.util.ext.isValidEmail
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


data class SignUpScreenState(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
) {
    private val isConfirmed = password == confirmPassword

    val isEmailValid
        get(): Boolean {
            if (email.isEmpty()) return true
            return email.isValidEmail()
        }

    val isPhoneNumberValid
        get(): Boolean {
            if (phoneNumber.isEmpty()) return true
            return phoneNumber.isInternationalPhoneNumber()
        }

    val isPasswordValid
        get(): Boolean {
            if (password.isEmpty()) return true
            return password.isStrongPassword()
        }

    val isPasswordConfirmed
        get(): Boolean {
            if (confirmPassword.isEmpty()) return true
            return isConfirmed
        }

    val isValid
        get(): Boolean {
            return !isLoading && isConfirmed && email.isValidEmail() && password.isStrongPassword()
        }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(navigator: Navigator) {

    val viewModel = koinViewModel(vmClass = AuthViewModel::class)

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val snackbar = koinInject<SnackbarHostState>()

    val (state, setState) = remember { mutableStateOf(SignUpScreenState()) }

    val auth by viewModel.auth.collectAsState(null)
    val error by viewModel.error.collectAsState(null)
    val isLoading by viewModel.loading.collectAsState(false)
    val notification by viewModel.notification.collectAsState(null)

    LaunchedEffect(auth) {
        when (auth) {
            is AuthState.Registered -> {
                snackbar.showSnackbar("Sign Up Successful")
                navigator.goBack()
            }

            else -> {}
        }
    }

    LaunchedEffect(notification) {
        notification?.message?.let {
            snackbar.showSnackbar(it)
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
                painter = painterResource("icon.png"),
                contentDescription = "Logo",
                modifier = Modifier.padding(BrandSize.md).width(398.dp).height(128.dp),
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    BrandSize.lg, Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.Top,
            ) {
                AppTextField(
                    value = state.firstName,
                    action = ImeAction.Next,
                    placeholder = "First Name",
                    modifier = Modifier.weight(1f),
                ) {
                    setState(state.copy(firstName = it))
                }
                AppTextField(
                    action = ImeAction.Next,
                    value = state.lastName,
                    placeholder = "Last Name",
                    modifier = Modifier.weight(1f),
                ) {
                    setState(state.copy(lastName = it))
                }
            }
        }

        item {
            AppTextField(
                value = state.email,
                action = ImeAction.Next,
                placeholder = "Email Address",
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
                value = state.phoneNumber,
                action = ImeAction.Next,
                placeholder = "Phone Number",
                isError = !state.isPhoneNumberValid,
                keyboardType = KeyboardType.Phone,
                supportingText = if (!state.isPhoneNumberValid) {
                    {
                        Text("Please provide a phone number")
                    }
                } else null,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Phone),
                        contentDescription = "Phone Icon",
                    )
                },
            ) {
                setState(state.copy(phoneNumber = it.filter { char -> char.isPhoneNumberChar() }))
            }
        }

        item {
            AppTextField(
                value = state.password,
                action = ImeAction.Next,
                placeholder = "Password",
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Lock),
                        contentDescription = "PadLock Icon",
                    )
                },
                isError = !state.isPasswordValid,
                supportingText = if (!state.isPasswordValid) {
                    { Text("Please use a stronger password") }
                } else null,
            ) {
                setState(state.copy(password = it))
            }
        }

        item {
            AppTextField(
                value = state.confirmPassword,
                action = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            viewModel.register(
                                email = state.email,
                                password = state.password,
                                firstName = state.firstName,
                                lastName = state.lastName,
                                phoneNumber = state.phoneNumber
                            )
                        }
                    }
                ),
                placeholder = "Confirm Password",
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Lock),
                        contentDescription = "PadLock Icon",
                    )
                },
                isError = !state.isPasswordConfirmed,
                supportingText = if (!state.isPasswordConfirmed) {
                    { Text("Password confirmation does not match password") }
                } else null,
            ) {
                setState(state.copy(confirmPassword = it))
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
                    text = "Sign Up",
                    loading = isLoading,
                    enabled = state.isValid,
                    type = AppButtonVariant.Primary,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.register(
                                email = state.email,
                                password = state.password,
                                firstName = state.firstName,
                                lastName = state.lastName,
                                phoneNumber = state.phoneNumber
                            )
                        }
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
                        text = "Already have an account?",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 16.sp,
                            lineHeight = 19.2.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    AppTextButton(
                        text = "Login",
                        onClick = {
                            navigator.goBack()
                            navigator.navigate(
                                AppRoute.LOGIN, options = NavOptions(
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