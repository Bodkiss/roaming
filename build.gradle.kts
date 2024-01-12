plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.build.config) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
}
repositories {
    mavenCentral()
    maven("https://jogamp.org/deployment/maven")
}

