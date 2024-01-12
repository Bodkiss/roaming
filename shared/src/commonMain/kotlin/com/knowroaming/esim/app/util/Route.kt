package com.knowroaming.esim.app.util

import moe.tlaster.precompose.navigation.BackStackEntry

object AppRoute {
    const val MENU: String = "/menu"
    const val ABOUT: String = "/about"
    const val SUPPORT: String = "/support"
    const val FAQ: String = "/support/faq"
    const val WHATSAPP: String = "/whatsapp"
    const val PRIVACY_POLICY: String = "/privacy_policy"
    const val TERMS_AND_CONDs: String = "/terms_&_conditions"
    const val EMAIL : String = "/email"

    const val AUTH: String = "/auth"
    const val LOGIN: String = "/auth/log_in"
    const val SIGN_UP: String = "/auth/sign_up"
    const val SIGN_OUT: String = "/auth/sign_out"
    const val SIGN_UP_OTP: String = "/auth/sign_up_otp"

    const val STORE: String = "/store"
    const val POPULAR: String = "/store/popular"
    const val DESTINATIONS: String = "/store/destinations"
    const val DESTINATION: String = "/store/destinations/{code}"
    const val DESTINATIONS_All: String = "/store/destinations/all"
    const val DESTINATIONS_GLOBAL: String = "/store/destinations/2A"
    const val DESTINATIONS_REGIONS: String = "/store/destinations/regions"

    const val PRODUCT: String = "/store/product"
    const val DESTINATION_PRODUCT_DETAILS: String = "/store/product/{code}/{package}"
    const val DESTINATION_PRODUCT_PURCHASED: String = "/store/product/{code}/{package}/purchased"

    const val MY_ESIMS: String = "/my_esims"
    const val ESIM_LIST: String = "/my_esims/iccids"
    const val ESIM_DETAILS: String = "/my_esims/iccids/{iccid}"
    const val ESIM_TOP_UP: String = "/my_esims/iccids/{iccid}/top_up"

    const val PROFILE: String = "/my_profile"
    const val PROFILE_DETAILS: String = "/my_profile/details"
    const val PROFILE_UPDATE: String = "/my_profile/details/{customer_id}/update"
}

val MainTabs = setOf(
    AppRoute.STORE, AppRoute.MY_ESIMS, AppRoute.PROFILE
)

fun String.isMainTabRoute(): Boolean {
    return MainTabs.any { route ->
        this.contains(
            route
        )
    }
}

fun BackStackEntry.isMainTabRoute(): Boolean {
    return this.route.route.isMainTabRoute()
}

fun BackStackEntry.isActive(route: String): Boolean {
    return this.hasRoute(route) || this.route.route.contains(route)
}