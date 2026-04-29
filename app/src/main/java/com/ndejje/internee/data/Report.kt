package com.ndejje.internee.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
