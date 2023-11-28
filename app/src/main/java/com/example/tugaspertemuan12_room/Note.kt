package com.example.tugaspertemuan12_room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Note(
    val id: String = "",
    val title: String,
    val deadline: String,
    val description: String
)
