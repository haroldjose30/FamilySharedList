package dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource

class KeyValueStorageRepository(
    val keyValueStorageDataSource: IKeyValueStorageDataSource
): IKeyValueStorageRepository {

    override fun put(key: KeyValueStorageRepositoryEnum, value: Int) {
        keyValueStorageDataSource.put(key.value, value)
    }

    override fun put(key: KeyValueStorageRepositoryEnum, value: String) {
        keyValueStorageDataSource.put(key.value, value)
    }

    override fun put(key: KeyValueStorageRepositoryEnum, value: Boolean) {
        keyValueStorageDataSource.put(key.value, value)
    }

    override fun getInt(key: KeyValueStorageRepositoryEnum, default: Int): Int
            =  keyValueStorageDataSource.getInt(key.value, default)

    override fun getString(key: KeyValueStorageRepositoryEnum) : String?
            =  keyValueStorageDataSource.getString(key.value)

    override fun getBool(key: KeyValueStorageRepositoryEnum, default: Boolean): Boolean =
        keyValueStorageDataSource.getBool(key.value, default)
}