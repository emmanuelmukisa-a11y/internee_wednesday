package com.ndejje.internee.data

import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {
    suspend fun insertUser(user: User) = appDao.insertUser(user)
    suspend fun insertUsers(users: List<User>) = appDao.insertUsers(users)
    suspend fun getUserByEmail(email: String): User? = appDao.getUserByEmail(email)
    fun getAllUsers(): Flow<List<User>> = appDao.getAllUsers()
    fun getUsersByRole(role: String): Flow<List<User>> = appDao.getUsersByRole(role)
    suspend fun assignSupervisor(studentId: Int, supervisorId: Int) = appDao.assignSupervisor(studentId, supervisorId)

    suspend fun insertInternship(internship: Internship) = appDao.insertInternship(internship)
    suspend fun insertInternships(internships: List<Internship>) = appDao.insertInternships(internships)
    fun getAllInternships(): Flow<List<Internship>> = appDao.getAllInternships()

    suspend fun insertReport(report: Report) = appDao.insertReport(report)
    suspend fun insertReports(reports: List<Report>) = appDao.insertReports(reports)
    fun getReportsForStudent(studentId: Int): Flow<List<Report>> = appDao.getReportsForStudent(studentId)
    fun getReportsForSupervisor(supervisorId: Int): Flow<List<Report>> = appDao.getReportsForSupervisor(supervisorId)
    fun getAllReports(): Flow<List<Report>> = appDao.getAllReports()
}
