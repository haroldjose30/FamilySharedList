plugins {
    val kotlinVersion = "1.7.10"

    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version(kotlinVersion).apply(false)
    kotlin("multiplatform").version(kotlinVersion).apply(false)
    kotlin("plugin.serialization").version(kotlinVersion).apply(true)
    id("com.codingfeline.buildkonfig").version("0.13.3").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
