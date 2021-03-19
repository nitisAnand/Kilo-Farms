package com.nitish.kilofarms.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nitish.kilofarms.DataX

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("SELECT * FROM item_table")
    fun readAllItem(): List<DataX>

    @Insert
    fun insertMultipleItem(vararg item: Item)

}