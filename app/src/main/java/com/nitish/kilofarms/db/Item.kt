package com.nitish.kilofarms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey
    @ColumnInfo(name = "skuId")
    val skuId: String,
    @ColumnInfo(name = "skuName")
    val skuName: String
)
