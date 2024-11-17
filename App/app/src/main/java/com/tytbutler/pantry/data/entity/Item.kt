package com.tytbutler.Pantry.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Item (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val category: Category,
    val isNeeded: Boolean
) {
    enum class Category {
        Produce { override fun toString(): String = "Produce" },
        Dry { override fun toString(): String = "Dry Goods" },
        Animal { override fun toString(): String = "Animal Product"},
        Pharm { override fun toString(): String = "Pharmacy" },
        Kitchen { override fun toString(): String = "Kitchen" },
        Misc { override fun toString(): String = "Miscellaneous" },
        Err { override fun toString(): String = "ERROR" };
        companion object {
            fun idx(cat: Category): Int {
                return when (cat) {
                    Produce -> 1
                    Dry -> 2
                    Pharm -> 3
                    Kitchen -> 4
                    Misc -> 5
                    Animal -> 6
                    Err -> -1
                }
            }
            fun fromInt(i: Int): Category {
                return when (i) {
                    1 -> Produce
                    2 -> Dry
                    3 -> Animal
                    4 -> Pharm
                    5 -> Kitchen
                    6 -> Misc
                    else -> Err
                }
            }
        }
    }
    companion object {
        fun nameToId(name: String): String = name
            .lowercase()
            .filter{it.code in 1..127}
            .replace(' ', '-')
        fun empty(isNeeded: Boolean): Item =
            Item(id = "", name = "", category = Category.Misc, isNeeded = isNeeded)

    }
}

@Entity(tableName = "itemFts")
@Fts4(contentEntity = Item::class)
data class ItemFts(
    val id: String,
    val name: String
)