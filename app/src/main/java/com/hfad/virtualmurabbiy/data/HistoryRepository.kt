package com.hfad.virtualmurabbiy.data

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {

    val getAllData: LiveData<List<HistoryData>> = historyDao.getAllData()

    suspend fun insertData(historyData: HistoryData) {
        historyDao.insertData(historyData)
    }
    suspend fun deleteAll() {
        historyDao.deleteAll()
    }

}