plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.skie)
    alias(libs.plugins.google.ksp)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvmToolchain(11)

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.arrow.core)
            implementation(libs.arrow.optics)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.skie.annotations)
            api(libs.kmm.viewmodel)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.arrow.optics.ksp)
    add("kspAndroid", libs.arrow.optics.ksp)
    add("kspIosX64", libs.arrow.optics.ksp)
    add("kspIosArm64", libs.arrow.optics.ksp)
    add("kspIosSimulatorArm64", libs.arrow.optics.ksp)
}

android {
    namespace = "com.jetbrains.kmpapp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
