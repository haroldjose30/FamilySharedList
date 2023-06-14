package dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage

interface IKeyValueStorageDataSource {
    fun put(key: String, value: Int)
    fun put(key: String, value: String)
    fun put(key: String, value: Boolean)
    fun getInt(key: String, default: Int): Int
    fun getString(key: String) : String?
    fun getBool(key: String, default: Boolean): Boolean
}