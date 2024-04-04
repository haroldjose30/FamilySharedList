package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

const val maxChar = 5
@Composable
fun SettingsItemWithInputTextView(
    title: String,
    subtitle: String,
    onClick: (text:String) -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp,8.dp,16.dp,0.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(16.dp,0.dp,16.dp,8.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                TextField(
                    value = textFieldState,
                    onValueChange = {
                        if (it.length <= maxChar) textFieldState = it
                    },
                    placeholder = { Text("ex: XY75K") },
                    singleLine = true,
                    modifier = Modifier
                        .padding(16.dp,0.dp,16.dp,8.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    onClick(textFieldState)
                },
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = title
                )
            }
        }
        HorizontalDivider()
    }
}