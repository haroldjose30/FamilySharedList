plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val koinCoreVersion = extra["koin.core.version"] as String
        val koinAndroidVersion = extra["koin.android.version"] as String
        val koinAndroidComposeVersion = extra["koin.android.compose.version"] as String

        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))

                implementation("androidx.compose.ui:ui-tooling:1.5.1")
                implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)


                implementation("io.insert-koin:koin-core:$koinCoreVersion")
                implementation("io.insert-koin:koin-android:$koinAndroidVersion")
                implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "dev.haroldjose"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "dev.haroldjose.familysharedlist.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
