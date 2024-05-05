package dev.haroldjose.familysharedlist.android.presentationLayer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope

private data class QuantityState(
    val quantity: Int,
    val minValue: Int,
    val maxValue: Int
) {
    val minusDisabled: Boolean
        get() = quantity <= minValue

    val moreDisabled: Boolean
        get() = quantity >= maxValue
}

@Composable
fun QuantitySelectionView(
    value: Int,
    incValue: Int = 1,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    onValueChanged: (newValue: Int) -> Unit
) {

    //region STATE
    var quantityState by remember { mutableStateOf(
        QuantityState(
            quantity = value,
            minValue = minValue,
            maxValue = maxValue
        )
    ) }
    //endregion

    //region FUNCTIONS

    quantityState.useDebounce(coroutineScope = coroutineScope){
        if (it.quantity != value) {
            Logger.d("QuantitySelectionView.useDebounce", it.toString())
            onValueChanged(it.quantity)
        }
    }

    fun internalOnMinusClicked(){

        if (quantityState.quantity > minValue) {

            quantityState = quantityState.copy(
                quantity = quantityState.quantity - incValue
            )
        }
    }

    fun internalOnMoreClicked(){

        if (quantityState.quantity < maxValue) {

            quantityState = quantityState.copy(
                quantity = quantityState.quantity + incValue
            )
        }
    }

    //endregion


    //region UI

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        IconButton(
            onClick = { internalOnMinusClicked() },
            enabled = quantityState.minusDisabled.not()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_minus),
                "minus",
                tint = if (quantityState.minusDisabled) Color.LightGray else Color.DarkGray
            )
        }

        Text(
            text = "${quantityState.quantity}",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )

        IconButton(
            onClick = {
                internalOnMoreClicked()
            },
            enabled = quantityState.moreDisabled.not()

        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "more",
                tint = if (quantityState.moreDisabled) Color.LightGray else Color.DarkGray
            )
        }
    }
    //endregion
}


@Preview
@Composable
fun QuantitySelectionView_Preview() {
    MyApplicationTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val coroutineScope = rememberCoroutineScope()
            val value = remember { mutableStateOf(1) }
            QuantitySelectionView(
                value = value.value,
                incValue = 1,
                minValue = 1,
                maxValue = 10,
                coroutineScope = coroutineScope,
                onValueChanged = {
                    value.value = it
                }
            )
            Text("onValueChanged")
            Text(value.value.toString())
        }

    }
}