package dev.haroldjose.familysharedlist.android.presentationLayer.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme

@Composable
fun ErrorPage(
    message: String,
    retryAction: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = "Algo deu errado",
            modifier = Modifier.requiredSize(48.dp),
            tint = Color.Gray
        )
        Text(
            text = "Ooops!",
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        retryAction?.let {
            Button(onClick = it) {
                Text(text = "Tente novamente")
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Preview
@Composable
fun ErrorPage_Preview() {
    MyApplicationTheme {
        ErrorPage(
            message = "Message de Erro - Preview",
            retryAction = {}
        )
    }
}