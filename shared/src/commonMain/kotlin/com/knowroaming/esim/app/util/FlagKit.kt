package com.knowroaming.esim.app.util

class FlagKit {

    private val url = "https://cdn.jsdelivr.net/gh/madebybowtie/FlagKit@2.4/Assets/SVG"

    companion object {
        private val instance: FlagKit
            get() = FlagKit()

        fun getUrl(countryCode: String): String {
            return "${instance.url}/${countryCode}.svg"
        }
    }
}