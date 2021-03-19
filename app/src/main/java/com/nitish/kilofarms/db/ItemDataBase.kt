package com.nitish.kilofarms.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nitish.kilofarms.HomeActivity

@Database(
    entities = arrayOf(Item::class),
    version = 1
)

abstract class ItemDataBase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var INSTANCE: ItemDataBase? = null

        fun getDataBase(context: HomeActivity): ItemDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDataBase::class.java,
                    "item.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}