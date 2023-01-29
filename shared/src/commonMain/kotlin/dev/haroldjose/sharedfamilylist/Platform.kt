package dev.haroldjose.sharedfamilylist

interface IPlatform {
    val name: String
}

expect fun getPlatform(): IPlatform
expect val isDebug: Boolean