package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource

interface IPlatform {
    val name: String
    val isDebug: Boolean
    fun generateUUID(): String
    fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource
    fun openUrlOnDefaultBrowser(url: String)
    fun openShareOptionsWithText(text: String)
}

expect fun getPlatform(): IPlatform