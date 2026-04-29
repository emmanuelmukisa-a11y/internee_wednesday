package com.ndejje.internee.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "internships")
data class Internship(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val company: String,
    val description: String,
    val location: String
)
