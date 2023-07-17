package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource

interface IPlatform {
    val name: String
}

expect fun getPlatform(): IPlatform
expect val isDebug: Boolean
expect fun generateUUID(): String
expect fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource
expect fun openUrlOnDefaultBrowser(url: String)
expect fun openShareOptionsWithText(text: String)