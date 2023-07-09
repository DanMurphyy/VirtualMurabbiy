package com.hfad.virtualmurabbiy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryData(
    @PrimaryKey
    var date: String,
)
