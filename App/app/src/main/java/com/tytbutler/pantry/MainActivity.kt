package com.tytbutler.pantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.tytbutler.Pantry.data.db.AppDatabase
import com.tytbutler.pantry.ui.theme.PantryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "pantry-db"
        ).build()
        val itemDao = db.itemDao();
        val recipeDao = db.recipeDao();
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PantryTheme {
        Greeting("Android")
    }
}