plugins {
    //trick: for the same plugin versions in all sub-modules
    //defaults plugins
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    //added plugins
    alias(libs.plugins.serialization).apply(true)
    alias(libs.plugins.buildkonfig).apply(false)
    alias(libs.plugins.gms.googleServices).apply(false)
    alias(libs.plugins.firebase.crashlitycs).apply(false)
}
