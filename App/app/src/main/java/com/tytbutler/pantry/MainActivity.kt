package com.tytbutler.pantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.tytbutler.Pantry.data.db.AppDatabase
import com.tytbutler.pantry.ui.screens.Screens
import com.tytbutler.pantry.ui.screens.list.ListScreen
import com.tytbutler.pantry.ui.theme.PantryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantryTheme {
                Main()
            }
        }
    }
}
@Composable
fun Main() {
    ListScreen()
}

@Preview
@Composable
fun MainPrev() {
    Main()
}
