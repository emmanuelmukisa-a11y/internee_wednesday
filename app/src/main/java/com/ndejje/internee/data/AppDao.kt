package com.ndejje.internee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User operations
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE role = :role")
    fun getUsersByRole(role: String): Flow<List<User>>

    @Query("UPDATE users SET supervisorId = :supervisorId WHERE id = :studentId")
    suspend fun assignSupervisor(studentId: Int, supervisorId: Int)

    // Internship operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInternship(internship: Internship)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInternships(internships: List<Internship>)

    @Query("SELECT * FROM internships")
    fun getAllInternships(): Flow<List<Internship>>

    // Report operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<Report>)

    @Query("SELECT * FROM reports WHERE studentId = :studentId")
    fun getReportsForStudent(studentId: Int): Flow<List<Report>>

    @Query("SELECT r.* FROM reports r JOIN users u ON r.studentId = u.id WHERE u.supervisorId = :supervisorId")
    fun getReportsForSupervisor(supervisorId: Int): Flow<List<Report>>

    @Query("SELECT * FROM reports")
    fun getAllReports(): Flow<List<Report>>
}
