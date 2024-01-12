package com.knowroaming.esim.app.presentation.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.input.AppPinTextField
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.components.main.AppTextButton
import com.knowroaming.esim.app.presentation.model.AuthViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class VerifyEmailScreenState(
    val code: String = "",
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun VerifyEmailScreen(navigator: Navigator) {
    val authViewModel = koinViewModel(vmClass = AuthViewModel::class)

    val listState = rememberLazyListState()
    val (state, setState) = remember { mutableStateOf(VerifyEmailScreenState()) }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(BrandSize.lg, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            start = BrandSize.lg,
            end = BrandSize.lg,
            bottom = BrandSize.x2l
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
            Text(
                text = "Please input the OTP included in the confirmation message sent to the provided email address:",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }

        item {
            AppPinTextField(
                onValueChange = {
                    setState(state.copy(code = it))
                },
            )
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
                    type = AppButtonVariant.Primary,
                    text = "Submit",
                    onClick = {
                        authViewModel.verifyEmail(state.code)
                    },
                )

                AppTextButton(
                    text = "Resend Code",
                    onClick = {

                    },
                )
            }
        }
    }
}