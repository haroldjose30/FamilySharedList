import dev.haroldjose.familysharedlist.domainLayer.usecases.account.Constants

private fun randomString(
    length: Int,
    charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
): String {
    val allowedCharsPoll: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val selectedCharPoll: List<Char> = charPool.filter { allowedCharsPoll.contains(it) }
    return List(length) { selectedCharPoll.random() }.joinToString("")
}

private fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

fun generateShortCodeByUuid(uuid: String): String {

    //TODO: improve this code to avoid collision

    //remove prefix
    val uuidWithoutPrefix  = uuid.uppercase().replace(Constants.ACCOUNT_PREFIX.uppercase(), "")
    val distinctCharPool = uuidWithoutPrefix.toList().distinct()
    return randomString(
        length = 5,
        charPool = distinctCharPool
    ).uppercase()
}

