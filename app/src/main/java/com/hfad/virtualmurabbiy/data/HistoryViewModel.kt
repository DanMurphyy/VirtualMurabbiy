package com.hfad.virtualmurabbiy.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDao = HistoryDatabase.getInstance(application).historyDao()
    private val repository: HistoryRepository
    val getAllData: LiveData<List<HistoryData>>

    init {
        repository = HistoryRepository(historyDao)
        getAllData = repository.getAllData
    }

    fun insertData(historyData: HistoryData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(historyData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
}