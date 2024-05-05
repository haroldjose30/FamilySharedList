package dev.haroldjose.familysharedlist.dependecyInjection

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCObject
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import kotlin.reflect.KClass

@OptIn(BetaInteropApi::class)
data class SwiftType(
    val type: ObjCObject,
    val swiftClazz: KClass<*>,
)
@OptIn(BetaInteropApi::class)
fun SwiftType.getClazz(): KClass<*> =
    when (type) {
        is ObjCClass -> getOriginalKotlinClass(type)
        is ObjCProtocol -> getOriginalKotlinClass(type)
        else -> null
    }
        ?: swiftClazz