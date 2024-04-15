package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

@Composable
fun ColumnLeftDefault(
    item: MutableState<FamilyListModel>,
    viewModel: IFamilyListViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 8.dp, end = 0.dp, top = 8.dp, bottom = 8.dp)
    ) {
        if (item.value.product?.imageFrontSmallUrl != null) {
            //reference: https://developer.android.com/develop/ui/compose/graphics/images/customize
            SubcomposeAsyncImage(
                model = item.value.product?.imageFrontSmallUrl,
                loading = {
                    LinearProgressIndicator(color = Color.LightGray)
                },
                contentDescription = item.value.product?.productName,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(16.dp))
                    .padding(4.dp)
                    .clickable {
                        viewModel.openImage(item.value)
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ColumnLeftDefault_Preview() {
    val item = remember { mutableStateOf( Samples.FamilyList.nutella) }
    item.value.isCompleted = false
    item.value.isPrioritized = false
    MyApplicationTheme {
        ColumnLeftDefault(
            item = item,
            viewModel = FamilyListViewModelMocked()
        )
    }
}
