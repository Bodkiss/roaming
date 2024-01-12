import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.build.config)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.ktorfit)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)

            implementation(libs.androidx.material)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.activity.compose)
            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.stripe.android)
            implementation(libs.libphonenumber.android)
            implementation(libs.androidx.security.crypto)
            implementation(libs.secure.preferences.lib)
        }

        commonMain.dependencies {
            implementation(kotlin("reflect"))

            implementation(libs.image.loader)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            @OptIn(ExperimentalComposeLibrary::class) implementation(compose.components.resources)

            implementation(libs.koin.core)
            implementation(libs.ktor.core)
            implementation(libs.koin.compose)

            implementation(libs.ktorfit)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.precompose)
            implementation(libs.precompose.koin)
            implementation(libs.precompose.viewmodel)
            implementation(libs.compose.webview.multiplatform)
        }
    }
}


buildkonfig {
    objectName = "BuildConfig"
    packageName = "com.knowroaming.esim.app.util"

    defaultConfigs {
        buildConfigField(
            STRING, "API_KEY", gradleLocalProperties(rootDir).getProperty("api.key") ?: ""
        )
        buildConfigField(
            STRING, "API_URL", gradleLocalProperties(rootDir).getProperty("api.url") ?: ""
        )
        buildConfigField(
            STRING,
            "APP_PACKAGE",
            gradleLocalProperties(rootDir).getProperty("app.package") ?: "com.knowroaming.esim.app"
        )
    }
}

android {
    namespace = "com.knowroaming.esim.app"
    buildToolsVersion = "34.0.0"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        coreLibraryDesugaring(libs.desugar.jdk.libs)
        debugImplementation(libs.compose.ui.tooling)
    }
}

dependencies {
    with(libs.ktorfit.ksp) {
        add("kspCommonMainMetadata", this)
        add("kspAndroid", this)
        add("kspAndroidTest", this)
        add("kspIosX64", this)
        add("kspIosX64Test", this)
        add("kspIosArm64", this)
        add("kspIosArm64Test", this)
        add("kspIosSimulatorArm64", this)
        add("kspIosSimulatorArm64Test", this)
    }
}