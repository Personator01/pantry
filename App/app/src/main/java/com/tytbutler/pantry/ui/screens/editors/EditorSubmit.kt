package com.tytbutler.pantry.ui.screens.editors

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.util.Either
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EditorSubmit(
    onSubmit: () -> Either<Unit, String>,
)
{
    var alert_message by remember { mutableStateOf("") }
    var is_alert_open by remember { mutableStateOf(false) }
    Button(
        onClick = {
            val r = onSubmit();
            when (r) {
                is Either.Left -> Unit
                is Either.Right -> {alert_message = r.right; is_alert_open = true}
            }
        }
    )
    {
        Text("Save")
    }
    if (is_alert_open) {
        AppDialog(
            onDismiss = {is_alert_open = false},
            message = alert_message
        )
    }
}