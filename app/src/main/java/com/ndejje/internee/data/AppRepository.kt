package com.ndejje.internee.data

import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {
    suspend fun insertUser(user: User) = appDao.insertUser(user)
    suspend fun getUserByEmail(email: String): User? = appDao.getUserByEmail(email)

    suspend fun insertInternship(internship: Internship) = appDao.insertInternship(internship)
    fun getAllInternships(): Flow<List<Internship>> = appDao.getAllInternships()

    suspend fun insertReport(report: Report) = appDao.insertReport(report)
    fun getReportsForStudent(studentId: Int): Flow<List<Report>> = appDao.getReportsForStudent(studentId)
    fun getAllReports(): Flow<List<Report>> = appDao.getAllReports()
}
