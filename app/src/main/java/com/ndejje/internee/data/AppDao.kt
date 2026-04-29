package com.ndejje.internee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User operations
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Internship operations
    @Insert
    suspend fun insertInternship(internship: Internship)

    @Query("SELECT * FROM internships")
    fun getAllInternships(): Flow<List<Internship>>

    // Report operations
    @Insert
    suspend fun insertReport(report: Report)

    @Query("SELECT * FROM reports WHERE studentId = :studentId")
    fun getReportsForStudent(studentId: Int): Flow<List<Report>>

    @Query("SELECT * FROM reports")
    fun getAllReports(): Flow<List<Report>>
}
