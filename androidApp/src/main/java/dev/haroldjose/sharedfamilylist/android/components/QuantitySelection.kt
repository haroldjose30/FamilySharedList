import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.sharedfamilylist.android.MyApplicationTheme
import kotlinx.coroutines.launch

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
    onValueChanged: (newValue: Int) -> Unit
) {


    //region STATE
    val quantityState = remember { mutableStateOf(
        QuantityState(
            quantity = value,
            minValue = minValue,
            maxValue = maxValue
        )
    ) }
    //endregion

    //region FUNCTIONS

    fun internalOnMinusClicked(){

        if (quantityState.value.quantity > minValue) {

            quantityState.value = quantityState.value.copy(
                quantity = quantityState.value.quantity - incValue
            )
            onValueChanged(quantityState.value.quantity)
        }
    }

    fun internalOnMoreClicked(){

        if (quantityState.value.quantity < maxValue) {

            quantityState.value = quantityState.value.copy(
                quantity = quantityState.value.quantity + incValue
            )
            onValueChanged(quantityState.value.quantity)
        }
    }

    //endregion


    //region UI

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = { internalOnMinusClicked() },
            enabled = quantityState.value.minusDisabled.not()
        ) {
            Icon(
                Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "minus",
                tint = if (quantityState.value.minusDisabled) Color.LightGray else Color.Black
            )
        }

        Text(
            text = "${quantityState.value.quantity}",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )

        IconButton(
            onClick = {
                internalOnMoreClicked()
            },
            enabled = quantityState.value.moreDisabled.not()

        ) {
            Icon(
                Icons.Rounded.KeyboardArrowRight,
                contentDescription = "more",
                tint = if (quantityState.value.moreDisabled) Color.LightGray else Color.Black
            )
        }
    }

    //endregion


}

@Preview(showBackground = false)
@Composable
fun DefaultPreviewQuantitySelectionView() {

    MyApplicationTheme {
        QuantitySelectionView(
            value = 1,
            incValue = 1,
            minValue = 1,
            maxValue = 10,
            onValueChanged = {
                Log.d(null,"onValueChanged:$it")
            }
        )
    }
}
