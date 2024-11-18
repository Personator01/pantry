package com.tytbutler.pantry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReturnBar(onBack: () -> Unit) {
    Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth()
        .padding(top = 100.dp, start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onBack) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, "Return")
        }
    }
}