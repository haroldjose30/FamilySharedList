plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dev.haroldjose.familysharedlist.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "dev.haroldjose.familysharedlist.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dependencies {
        val coroutinesVersion: String by project
        val koinVersion: String by project
        val koinAndroidVersion: String by project
        val koinAndroidComposeVersion: String by project
        val composeUi: String by project
        val composeFoundation: String by project
        val composeMaterial: String by project
        val activityCompose: String by project
        val kotlinxDatetime: String by project

        implementation(project(":shared"))
        implementation("androidx.compose.ui:ui:$composeUi")
        implementation("androidx.compose.ui:ui-tooling:$composeUi")
        implementation("androidx.compose.ui:ui-tooling-preview:$composeUi")
        implementation("androidx.compose.foundation:foundation:$composeFoundation")
        implementation("androidx.compose.material:material:$composeMaterial")
        implementation("androidx.navigation:navigation-compose:2.6.0")
        implementation("androidx.activity:activity-compose:$activityCompose")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
        implementation("io.insert-koin:koin-core:$koinVersion")
        implementation("io.insert-koin:koin-android:$koinAndroidVersion")
        implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetime")
    }
}