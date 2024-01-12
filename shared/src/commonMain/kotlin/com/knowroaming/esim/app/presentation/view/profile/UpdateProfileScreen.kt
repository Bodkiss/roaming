package com.knowroaming.esim.app.presentation.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.domain.service.AuthState
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.input.AppTextField
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.view.auth.SignUpScreenState
import com.knowroaming.esim.app.util.ext.isPhoneNumberChar
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun UpdateProfileScreen(navigator:Navigator) {

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val viewModel = koinViewModel(vmClass = AuthViewModel::class)

    val auth by viewModel.auth.collectAsState(null)

    val snackbar = koinInject<SnackbarHostState>()




    val customer by koinInject<Flow<Customer?>>(named(AppKoin.AUTH_USER)).collectAsState(null)

    val (state, setState) = remember {
        mutableStateOf(
            SignUpScreenState(
                email = customer?.email ?: "",
                firstName = customer?.firstName ?: "",
                lastName = customer?.lastName ?: "",
                phoneNumber = customer?.phoneNumber ?: "",
            )
        )
    }

    LaunchedEffect(auth) {
        println("UpdateProfileScreen auth $auth")
        when (auth) {
            is AuthState.Updated -> {
                snackbar.showSnackbar("Updated Successful")
                navigator.goBack()
            }

            else -> {}
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(BrandSize.lg),
        verticalArrangement = Arrangement.spacedBy(
            BrandSize.lg, Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

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
            AppButton(type = AppButtonVariant.Primary, text = "Submit Changes",
                enabled = state.isValid,
                onClick = {
                coroutineScope.launch {
                    viewModel.editDetails(
                        email = state.email,
                        password = state.password,
                        firstName = state.firstName,
                        lastName = state.lastName,
                        phoneNumber = state.phoneNumber
                    )
                }
            })
        }
    }
}