package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem.FamilyListRowItemWithProduct
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FamilyListRowSwipeToDismiss(
    item: FamilyListModel,
    viewModel: IFamilyListViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.Settled -> {
                    return@rememberSwipeToDismissBoxState false
                }

                SwipeToDismissBoxValue.StartToEnd -> {
                    coroutineScope.launch {
                        viewModel.updateIsCompleted(uuid = item.uuid, isCompleted = true)
                    }
                    return@rememberSwipeToDismissBoxState true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    coroutineScope.launch {
                        viewModel.remove(uuid = item.uuid)
                    }
                    return@rememberSwipeToDismissBoxState true
                }
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState) },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
    ) {
        FamilyListRowItemWithProduct(
            item = item,
            viewModel = viewModel,
            bottomSheetScaffoldState = bottomSheetScaffoldState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.Settled -> Color.Transparent
            SwipeToDismissBoxValue.StartToEnd -> Color.Green
            SwipeToDismissBoxValue.EndToStart -> Color.Red
        }, label = "ColorAnimation"
    )
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Comprado"
            )
            Text("Comprado")
        }
        Spacer(modifier = Modifier)
        if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
            Text("Excluir")
            Icon(
                Icons.Default.Delete,
                contentDescription = "Excluir"
            )
        }
    }
}