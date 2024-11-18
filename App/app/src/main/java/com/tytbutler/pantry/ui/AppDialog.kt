package com.tytbutler.pantry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Preview
@Composable
fun AppDialogPreview() {
    AppDialog(message = "Message! \n",
        onDismiss = {},
        dismissText = "Dismiss",
        enableSecondButton = true,
        secondButtonText = "Second Button!",
    )
}

@Composable
fun AppDialog(
    message: String,
    onDismiss: () -> Unit,
    dismissText: String = "Dismiss",
    enableSecondButton: Boolean = false,
    secondButtonText: String = "",
    onSecondButton: () -> Unit = {},
) {
    Dialog (
        onDismissRequest = onDismiss,
    )
    {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (modifier = Modifier.padding(20.dp).fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                ){
                Text(message)
                Row (horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()) {
                    if (enableSecondButton) {
                        TextButton(
                            onClick = {
                                onSecondButton();
                                onDismiss();
                            }
                        ) {
                            Text(secondButtonText, textAlign = TextAlign.End)
                        }
                    }
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(dismissText, textAlign = TextAlign.End)
                    }
                }
            }
        }
    }
}