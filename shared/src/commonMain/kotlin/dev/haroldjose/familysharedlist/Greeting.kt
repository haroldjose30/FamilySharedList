package dev.haroldjose.familysharedlist

class Greeting {
    private val platform: IPlatform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}