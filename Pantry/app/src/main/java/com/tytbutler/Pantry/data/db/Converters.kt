package com.tytbutler.Pantry.data

import androidx.room.TypeConverter
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
}