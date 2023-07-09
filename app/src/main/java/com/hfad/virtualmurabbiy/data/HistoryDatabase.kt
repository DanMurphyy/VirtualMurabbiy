package com.hfad.virtualmurabbiy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryData::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}