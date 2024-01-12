package com.knowroaming.esim.app.presentation.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp


@Composable
fun AppTextField(
    value: String,
    placeholder: String,
    maxLines: Int = 1,
    minLines: Int = 1,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    action: ImeAction = ImeAction.Done,
    supportingText: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChanged: (FocusState) -> Unit = {},
    shape: Shape = MaterialTheme.shapes.extraSmall,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    colors: TextFieldColors = TextFieldDefaults.colors(
        cursorColor = MaterialTheme.colorScheme.onSurface,
        errorTextColor = MaterialTheme.colorScheme.secondary,
        errorLabelColor = MaterialTheme.colorScheme.secondary,
        errorSuffixColor = MaterialTheme.colorScheme.secondary,
        errorPrefixColor = MaterialTheme.colorScheme.secondary,
        errorCursorColor = MaterialTheme.colorScheme.secondary,
        errorContainerColor = MaterialTheme.colorScheme.surface,
        errorIndicatorColor = Color.Transparent,
        errorLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        errorPlaceholderColor = MaterialTheme.colorScheme.onSurface,
        errorTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        errorSupportingTextColor = MaterialTheme.colorScheme.secondary,
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
        disabledPrefixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledSuffixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledIndicatorColor = Color.Transparent,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
        focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
        focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        focusedIndicatorColor = Color.Transparent,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedPrefixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedSuffixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    ),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        fontWeight = FontWeight(400),
        color = MaterialTheme.colorScheme.onSurface,
    ),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {

    val isPassword = keyboardType == KeyboardType.Password

    var passwordVisibility by remember { mutableStateOf(false) }

    val passwordVisibilityTransformation =
        if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()

    val transformation = if (isPassword) passwordVisibilityTransformation else visualTransformation

    TextField(
        shape = shape,
        value = value,
        colors = colors,
        enabled = enabled,
        isError = isError,
        readOnly = readOnly,
        maxLines = maxLines,
        minLines = minLines,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions.copy(
            keyboardType = keyboardType,
            imeAction = action,
        ),
        visualTransformation = transformation,
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility },
                    enabled = enabled,
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Password Visibility",
                    )
                }
            } else {
                trailingIcon?.invoke()
            }
        },
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, style = textStyle) },
        modifier = modifier.fillMaxWidth().onFocusChanged(onFocusChanged),
    )
}