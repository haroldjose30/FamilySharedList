package dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage

interface IKeyValueStorageRepository {
    fun put(key: KeyValueStorageRepositoryEnum, value: Int)
    fun put(key: KeyValueStorageRepositoryEnum, value: String)
    fun put(key: KeyValueStorageRepositoryEnum, value: Boolean)
    fun getInt(key: KeyValueStorageRepositoryEnum, default: Int): Int
    fun getString(key: KeyValueStorageRepositoryEnum) : String?
    fun getBool(key: KeyValueStorageRepositoryEnum, default: Boolean): Boolean
}