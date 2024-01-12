package com.knowroaming.esim.app.util

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun OpenEmailClient(recipient: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822" // MIME type for email
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    if (intent.resolveActivity(LocalContext.current.packageManager) != null) {
        LocalContext.current.startActivity(intent)
    } else {
        Toast.makeText(LocalContext.current, "no email app is available", Toast.LENGTH_LONG).show()
        // Handle the case where no email app is available
    }
}

@Composable
actual fun OpenWhatsApp(message: String) {
    val context = LocalContext.current
    try {
        val encodedMessage = Uri.encode(message)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?text=$encodedMessage")

        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        val playStoreIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(playStoreIntent)
    }
}