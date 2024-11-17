package com.tytbutler.pantry.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AppDialog(
    message: String,
    onDismiss: () -> Unit,
    dismissText: String = "Dismiss",
    enableSecondButton: Boolean = false,
    secondButtonText: String = "",
    onSecondButton: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Dialog (
        onDismissRequest = onDismiss,
        modifier = modifier
    )
    {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                Text(message)
                Row {
                    if (enableSecondButton) {
                        TextButton(
                            onClick = {
                                onSecondButton();
                                onDismiss();
                            }
                        ) {
                            Text(secondButtonText)
                        }
                    }
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(dismissText)
                    }
                }
            }
        }
    }
}