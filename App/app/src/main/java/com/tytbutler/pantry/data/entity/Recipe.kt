package com.tytbutler.Pantry.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["key"], unique = true)])
@Fts4
data class Recipe (
    @PrimaryKey val uid: Int,
    val key: String,
    val name: String,
    val description: String,
    val ingredients: Set<String>
)