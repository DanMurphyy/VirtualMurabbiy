package com.hfad.virtualmurabbiy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(historyData: HistoryData)

    @Query("SELECT * FROM history_table ORDER BY date ASC")
    fun getAllData(): LiveData<List<HistoryData>>

    @Query("DELETE FROM history_table")
    fun deleteAll()
}