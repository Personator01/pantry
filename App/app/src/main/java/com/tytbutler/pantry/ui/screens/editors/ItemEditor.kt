package com.tytbutler.pantry.ui.screens.editors

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.tytbutler.Pantry.data.dao.ItemDao
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.util.Either

@Composable
fun ItemEditor(item: Item, itemDao: ItemDao) {
    var item_category by remember{ mutableStateOf(item.category) };

    Column (horizontalAlignment = Alignment.Start) {
        TextField(
            value = item_category.toString(),
            onValueChange = {
            }
        )
        EditorSubmit(
            onSubmit = {
                Either.Left(Unit)
            }
        )
    }
}
