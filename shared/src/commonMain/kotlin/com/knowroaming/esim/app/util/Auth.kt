package com.knowroaming.esim.app.util

import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpStatusCode


val AuthPlugin = createClientPlugin("AuthPlugin", ::AuthPluginConfig) {
    val token = pluginConfig.token

    on(Send) { request ->
        request.headers["Authorization"] = "Token $token"

        proceed(request).let {
            it.response.run {

                val prefix = headers["WWW-Authenticate"]

                if (status == HttpStatusCode.Unauthorized && prefix?.isNotEmpty() == true) {
                    request.headers["Authorization"] = "$prefix $token"
                    proceed(request)
                } else {
                    it
                }
            }
        }
    }
}

class AuthPluginConfig {
    var token: String = ""
}