package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod

typealias AJHttpHeaders = Map<String, String>
typealias AJHttpQueryParameters = Map<String, String>

interface IAJHttpRequest {
    val urlBase: String
    val path: String
    val method: AJHttpMethod
    val headers: AJHttpHeaders?
    val queryParameters: AJHttpQueryParameters?
    var body: Any?
}

