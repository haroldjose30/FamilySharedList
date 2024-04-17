import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.serialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.googleDevtools)
    alias(libs.plugins.nativecoroutines)
}

kotlin {
    //to avoid testClasses not found by compiler
    task("testClasses")

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "dev.haroldjose.familysharedlist"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


//To generate BuildKonfig file: ./gradlew -p shared generateBuildKonfig
buildkonfig {
    packageName = "dev.haroldjose.familysharedlist"

    // default config is required
    defaultConfigs {
        val apiKey: String by project
        val apiUrl: String by project

        buildConfigField(STRING, "apiKey", apiKey)
        buildConfigField(STRING, "apiUrl", apiUrl)
    }
}
