import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-10"
    id("com.codingfeline.buildkonfig") version "0.13.3"
    id("org.jetbrains.compose")
}

compose {
    kotlinCompilerPlugin.set("org.jetbrains.compose.compiler:compiler:1.4.7-rc01")
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }



    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "0.0.1"
        summary = "FamilySharedList KMM shared module"
        homepage = "https://github.com/haroldjose30/FamilySharedList"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "dev.haroldjose.familysharedlist.ios")
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }


    sourceSets {

        val ktorVersion: String by project
        val coroutinesVersion: String by project
        val serializationVersion: String by project
        val koinVersion: String by project
        val kotlinxDatetime: String by project

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetime")

                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val androidUnitTest by getting
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }
        val iosTest by getting {
            dependsOn(commonTest)
        }
    }
}

android {
    namespace = "dev.haroldjose.familysharedlist"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
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