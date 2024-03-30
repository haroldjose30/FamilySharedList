package dev.haroldjose.familysharedlist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform