package com.tytbutler.pantry.ui.screens.editors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.util.Either

@Composable
fun ItemCreator(createAsNeeded: Boolean, onSubmit: (Item) -> Unit, onBack: () -> Unit) {
    var item_name by remember { mutableStateOf("") }
    var item_category by remember{ mutableStateOf(Item.Category.Misc) };
    BackHandler(enabled = true, onBack = onBack)
    Column (horizontalAlignment = Alignment.Start) {
        ItemDropdown(Item.Category.Misc, {item_category = it})
        EditorSubmit(
            onSubmit = {
                if (true) { // validate
                    onSubmit(Item(-1,
                        Item.nameToId(item_name),
                        item_name,
                        item_category,
                        createAsNeeded
                        ))
                    Either.Left<Unit, String>(left = Unit)
                }
                Either.Right<Unit, String>("Failed to validate item");
            }
        )
    }
}
