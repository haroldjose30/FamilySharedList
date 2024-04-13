package dev.haroldjose.familysharedlist.android.extensions

import java.text.NumberFormat

/**
 * Returns a string with only the digits of the double
 */
fun Double.toStringWithOnlyDigits(): String = this.toString().filter { it.isDigit() }


/**
 * Returns a string with the currency format
 */
fun Double.ToCurrencyFormat(fractionDigits: Int = 2): String {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.setMaximumFractionDigits(fractionDigits);
    return numberFormat.format(this)
}


