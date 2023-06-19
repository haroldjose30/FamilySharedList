
private fun randomString(
    length: Int
): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return List(length) { charPool.random() }.joinToString("")
}

private fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

fun generateShortID(): String {
    return randomString(length = 5).uppercase()
}

