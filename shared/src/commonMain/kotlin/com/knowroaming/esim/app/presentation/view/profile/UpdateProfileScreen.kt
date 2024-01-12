package com.knowroaming.esim.app.presentation.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.input.AppTextField
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.view.auth.SignUpScreenState
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun UpdateProfileScreen() {

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

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
                    modifier = Modifier.weight(1f),
                    placeholder = "First Name",
                    value = state.firstName,
                    onFocusChanged = { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    },
                ) {
                    setState(state.copy(firstName = it))
                }
                AppTextField(
                    modifier = Modifier.weight(1f),
                    placeholder = "Last Name",
                    value = state.lastName,
                    onFocusChanged = { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(1)
                            }
                        }
                    },
                ) {
                    setState(state.copy(lastName = it))
                }
            }
        }

        item {
            AppTextField(
                placeholder = "Email Address",
                value = state.email,
                keyboardType = KeyboardType.Email,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Email),
                        contentDescription = "Email Icon",
                    )
                },
                onFocusChanged = { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(2)
                        }
                    }
                },
            ) {
                setState(state.copy(email = it))
            }
        }

        item {
            AppTextField(
                placeholder = "Phone Number",
                value = state.phoneNumber,
                keyboardType = KeyboardType.Phone,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Phone),
                        contentDescription = "Phone Icon",
                    )
                },
                onFocusChanged = { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(3)
                        }
                    }
                },
            ) {
                setState(state.copy(phoneNumber = it))
            }
        }

        item {
            AppTextField(
                placeholder = "Password",
                value = state.password,
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Lock),
                        contentDescription = "PadLock Icon",
                    )
                },
                onFocusChanged = { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(4)
                        }
                    }
                },
            ) {
                setState(state.copy(password = it))
            }
        }

        item {
            AppTextField(
                placeholder = "Confirm Password",
                value = state.confirmPassword,
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Lock),
                        contentDescription = "PadLock Icon",
                    )
                },
                onFocusChanged = { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(4)
                        }
                    }
                },
            ) {
                setState(state.copy(confirmPassword = it))
            }
        }

        item {
            AppButton(type = AppButtonVariant.Primary, text = "Submit Changes", onClick = {})
        }
    }
}