package dev.haroldjose.familysharedlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.AndroidKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import java.util.UUID

class AndroidPlatform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    //FIXME: harold
    override val isDebug: Boolean = false//BuildConfig.DEBUG
    override fun generateUUID() = UUID.randomUUID().toString()
    override fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource = AndroidKeyValueStorageDataSource()
    override fun openUrlOnDefaultBrowser(url: String) {
        var urlString = url
        if (!(urlString.startsWith("http://") || urlString.startsWith("https://"))) {
            urlString = "http://$url"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
        androidContextForKmm?.startActivity(browserIntent)
    }
    override fun openShareOptionsWithText(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        androidContextForKmm?.startActivity(Intent.createChooser(intent, "Compartilhar com:"))
    }

    companion object {
        var androidContextForKmm: Context? = null
    }
}

actual fun getPlatform(): IPlatform = AndroidPlatform()
