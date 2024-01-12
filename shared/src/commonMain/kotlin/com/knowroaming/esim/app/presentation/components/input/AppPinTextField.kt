package com.knowroaming.esim.app.presentation.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.theme.BrandSize


@Composable
fun AppPinTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
) {

    val focusManager = LocalFocusManager.current

    val (otpCode, setOtpCode) = remember { mutableStateOf("") }

    val (codes, setCodesState) = remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(otpCode) {
        onValueChange(otpCode)
    }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    fun getPinCodeAt(index: Int): String {
        return if (index < codes.size) {
            codes[index]
        } else {
            ""
        }
    }

    Row {
        repeat(5) { index ->
            TextField(
                value = getPinCodeAt(index),
                placeholder = {
                    Text(
                        text = "-",
                        modifier = modifier.fillMaxWidth()
                            .align(Alignment.CenterVertically)
                            .padding(BrandSize.xs)
                            .weight(1f),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            lineHeight = 19.2.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(400),
                        ),
                    )
                },
                onValueChange = { value ->
                    val values = codes.toMutableList()
                    val text = value.let { if (it.length == 1) it else "" }

                    if (index < codes.size) {
                        values[index] = text
                    } else {
                        values.add(text)
                    }

                    if (text.isEmpty() && values.isNotEmpty()) {
                        focusManager.moveFocus(FocusDirection.Previous)
                    } else {
                        focusManager.moveFocus(FocusDirection.Next)
                    }

                    setCodesState(values)
                    setOtpCode(values.joinToString(""))
                },
                modifier = modifier.fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(BrandSize.xs)
                    .weight(1f),
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(400),
                ),
                maxLines = 1,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Next,
                ),
                shape = MaterialTheme.shapes.extraSmall,
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    errorTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }
}