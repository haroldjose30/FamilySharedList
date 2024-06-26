import Foundation
import shared

//reference: https://medium.com/@0x6368656174/kotlin-multiplatform-dependency-injection-with-pure-native-services-6897d9c3bcaa

class SwiftKClass<T>: NSObject, KotlinKClass {
    func isInstance(value: Any?) -> Bool { value is T }
    var qualifiedName: String? { String(reflecting: T.self) }
    var simpleName: String? { String(describing: T.self) }
}

func KClass<T>(for type: T.Type) -> KotlinKClass {
    SwiftType(type: type, swiftClazz: SwiftKClass<T>()).getClazz()
}

extension Koin_coreScope {
    func get<T>() -> T {
        // swiftlint:disable force_cast
        get(clazz: KClass(for: T.self), qualifier: nil, parameters: nil) as! T
        // swiftlint:enable force_cast
    }
}

func koinInject<T>(
    qualifier: Koin_coreQualifier? = nil,
    parameters: (() -> Koin_coreParametersHolder)? = nil
) -> T {
    // swiftlint:disable force_cast
    KoinGetKt.koinGet(clazz: KClass(for: T.self), qualifier: qualifier, parameters: parameters) as! T
    // swiftlint:enable force_cast
}
