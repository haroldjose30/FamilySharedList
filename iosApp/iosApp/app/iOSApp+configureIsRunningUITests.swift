import shared

extension AppDelegate {

    func configureIsRunningUITests() {
        
#if DEBUG
        if CommandLine.arguments.contains("isRunningUITests") {
            
            GlobalState.companion.isRunningUITests = true
            
            
            if let keySample = ProcessInfo.processInfo.environment["keySample"],
               keySample == "valueSample" {
                print("keySample",keySample)
            }
            
            if let keySample2 = ProcessInfo.processInfo.environment["keySample2"] {
                print("keySample2",keySample2)
            }
        }
#endif
        
    }
}
