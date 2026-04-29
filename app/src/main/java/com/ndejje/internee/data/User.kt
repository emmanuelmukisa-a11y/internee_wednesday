package com.ndejje.internee.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val role: String, // "STUDENT", "SUPERVISOR", "ADMIN"
    val supervisorId: Int? = null // Only for students
)
