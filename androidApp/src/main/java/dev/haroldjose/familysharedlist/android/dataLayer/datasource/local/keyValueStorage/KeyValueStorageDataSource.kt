package dev.haroldjose.familysharedlist.android.dataLayer.datasource.local.keyValueStorage

import android.content.Context
import android.content.SharedPreferences
import dev.haroldjose.familysharedlist.android.app.MainApplication
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource


class KeyValueStorageDataSource(): IKeyValueStorageDataSource {

    private var sharedPreference: SharedPreferences? = null
    private fun getSharedPreferences(): SharedPreferences? {
        if (sharedPreference == null) {
            sharedPreference = MainApplication.applicationContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
        return sharedPreference
    }

    override fun put(key: String, value: Int) {
        getSharedPreferences()?.edit()?.putInt(key, value)?.apply()
    }

    override fun put(key: String, value: String) {
        getSharedPreferences()?.edit()?.putString(key, value)?.apply()
    }

    override fun put(key: String, value: Boolean) {
        getSharedPreferences()?.edit()?.putBoolean(key, value)?.apply()
    }

    override fun getInt(key: String, default: Int): Int {
        val result =  getSharedPreferences()?.getInt(key, default )
        return result ?: 0
    }

    override fun getString(key: String): String? {
        val result = getSharedPreferences()?.getString(key, null)
        return result
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        val result = getSharedPreferences()?.getBoolean(key, default)
        return result ?: false
    }

    companion object {
        private val SHARED_PREFERENCE_NAME = "FamilyListApp"
    }
}
