plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    alias(libs.plugins.serialization)
}

android {
    namespace = "dev.haroldjose.familysharedlist.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "dev.haroldjose.familysharedlist.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.navigation.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.coil.compose)

    implementation(libs.camera.camera2)
    implementation(libs.camera.core)
    implementation(libs.camera.extensions)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.camera.video)
    implementation(libs.barcode.scanning)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.kotlinx.datetime)


}