package dev.haroldjose.familysharedlist.dependencyInjection.koinIOSHelper

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools
import kotlin.reflect.KClass

//reference: https://medium.com/@0x6368656174/kotlin-multiplatform-dependency-injection-with-pure-native-services-6897d9c3bcaa
fun <T> koinGet(
    clazz: KClass<*>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): T {
    val koin = KoinPlatformTools.defaultContext().get()
    return koin.get(clazz, qualifier, parameters)
}