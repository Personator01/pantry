package com.tytbutler.Pantry.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["key"], unique = true)])
data class Item (
    @PrimaryKey  val uid: Int,
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "name") val name: String
)