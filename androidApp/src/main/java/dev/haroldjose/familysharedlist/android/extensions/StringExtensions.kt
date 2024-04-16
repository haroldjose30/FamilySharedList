package dev.haroldjose.familysharedlist.android.extensions

import androidx.core.text.isDigitsOnly
import java.text.DecimalFormat

/**
 * used to validate if a string is a valid amount
 */
fun String.isValidFormattableAmount(maxLength: Int = 6): Boolean {
    return isNotBlank() && isDigitsOnly() && length <= maxLength
}

/**
 * used to return the currency symbol
 */
fun getCurrencySymbol(): String {
    val symbols = DecimalFormat().decimalFormatSymbols
    return symbols.currencySymbol
}