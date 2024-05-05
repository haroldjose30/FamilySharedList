import Foundation
import shared

class KeyValueStorageDataSource: IKeyValueStorageDataSource {

    func put(key: String, value_ value: Int32) {
        UserDefaults.standard.set(value, forKey: key)
    }

    func put(key: String, value__ value: String) {
        UserDefaults.standard.set(value, forKey: key)
    }

    func put(key: String, value: Bool) {
        UserDefaults.standard.set(value, forKey: key)
    }

    func getInt(key: String, default: Int32) -> Int32 {
        return UserDefaults.standard.integer(forKey: key).toInt32()
    }

    func getString(key: String) -> String? {
        return UserDefaults.standard.string(forKey: key)
    }

    func getBool(key: String, default: Bool) -> Bool {
        return UserDefaults.standard.bool(forKey: key)
    }
}
