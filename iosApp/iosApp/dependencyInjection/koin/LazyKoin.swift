
@propertyWrapper
struct LazyKoin<T> {
    lazy var wrappedValue: T = {
        koinInject()
    }()
    
    init() { }
}
