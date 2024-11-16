package com.tytbutler.Pantry.data.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity
data class Recipe (
    @PrimaryKey(autoGenerate = false)
    val key: String,
    val name: String,
    val description: String,
    val ingredients: Set<String>
)