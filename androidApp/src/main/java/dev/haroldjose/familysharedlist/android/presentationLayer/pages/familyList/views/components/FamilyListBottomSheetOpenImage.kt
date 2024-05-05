package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FamilyListBottomSheetOpenImage(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: IFamilyListViewModel
): @Composable() (ColumnScope.() -> Unit) {
    val imageUrl = getImageUrl(viewModel)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = "FamilyListBottomSheetOpenImage") {
        bottomSheetScaffoldState.bottomSheetState.expand()
    }

    BackHandler {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.hide()
        }
    }

    return {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .requiredSize(100.dp),
                        color = Color.LightGray
                    )
                },
                contentDescription = viewModel.openImageSelectedItem?.product?.productName,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(16.dp))
                    .padding(4.dp)
            )
        }
    }
}

private fun getImageUrl(viewModel: IFamilyListViewModel): String {
    viewModel.openImageSelectedItem?.product?.imageUrl?.let {
        return it
    }

    viewModel.openImageSelectedItem?.product?.imageFrontUrl?.let {
        return it
    }

    viewModel.openImageSelectedItem?.product?.imageFrontSmallUrl?.let {
        return it
    }

    return "https://gamestation.com.br/wp-content/themes/game-station/images/image-not-found.png"
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FamilyListBottomSheetOpenImage_Preview() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val viewModel = FamilyListViewModelMocked()
    MyApplicationTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 128.dp,
            sheetContent = FamilyListBottomSheetOpenImage(bottomSheetScaffoldState, viewModel),
            content =  {
                Text("FamilyListBottomSheetOpenImage_Preview")
            }
        )
    }
}