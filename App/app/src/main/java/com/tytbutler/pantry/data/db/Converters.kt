package com.tytbutler.Pantry.data.db

import androidx.room.TypeConverter
import com.tytbutler.Pantry.data.entity.Item
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun setToJson(value: Set<String>): String {
        return Json.encodeToString(value);
    }

    @TypeConverter
    fun jsonToSet(value: String): Set<String> {
        return Json.decodeFromString<Set<String>>(value);
    }

    @TypeConverter
    fun categoryToInt(value: Item.Category): Int = Item.Category.idx(value)

    @TypeConverter
    fun intToCategory(value: Int): Item.Category = Item.Category.fromInt(value)
}