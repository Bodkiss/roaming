package com.knowroaming.esim.app

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.theme.AppTheme
import com.knowroaming.esim.app.util.SharedStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedStorage.configure(this)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    AppTheme {
        AppButton(type = AppButtonVariant.Muted, text = "Log In", onClick = {})
    }
}