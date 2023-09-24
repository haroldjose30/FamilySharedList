package dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage

import platform.Foundation.NSUserDefaults

class IOSKeyValueStorageDataSource: IKeyValueStorageDataSource {

    override fun put(key: String, value: Int) {
        NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
    }

    override fun put(key: String, value: String) {
        NSUserDefaults.standardUserDefaults.setObject(value, key)
    }

    override fun put(key: String, value: Boolean) {
        NSUserDefaults.standardUserDefaults.setBool(value, key)  
    }

    override fun getInt(key: String, default: Int): Int {
        return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
    }

    override fun getString(key: String): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(key)  
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        return NSUserDefaults.standardUserDefaults.boolForKey(key) 
    }
}