package com.tytbutler.Pantry.data.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity
data class Recipe (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String,
    val ingredients: Set<String>
) {
    companion object {
        fun empty(): Recipe = Recipe("", "", "", setOf())
        fun nameToId(name: String): String = name
            .lowercase()
            .filter{it.code in 1..127}
            .replace(' ', '-')
    }
}

@Entity
@Fts4(contentEntity = Recipe::class)
data class RecipeFts (
    val id: String,
    val name: String,
    val ingredients: Set<String>
)