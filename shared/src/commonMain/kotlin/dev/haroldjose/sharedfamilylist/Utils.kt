fun randomString(
    length: Int
): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return List(length) { charPool.random() }.joinToString("")
}

fun generateUUID(): String {
    //XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
  return randomString(length = 32)
      .addCharAtIndex(char = '-', index = 8)
      .addCharAtIndex(char = '-', index = 13)
      .addCharAtIndex(char = '-', index = 18)

}

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()