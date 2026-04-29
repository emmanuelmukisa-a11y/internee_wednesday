package com.ndejje.internee.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.data.Internship
import com.ndejje.internee.data.Report
import com.ndejje.internee.data.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    val allInternships: StateFlow<List<Internship>> = repository.getAllInternships()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allReports: StateFlow<List<Report>> = repository.getAllReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allUsers: StateFlow<List<User>> = repository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudents: StateFlow<List<User>> = repository.getUsersByRole("STUDENT")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSupervisors: StateFlow<List<User>> = repository.getUsersByRole("SUPERVISOR")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun assignSupervisor(studentId: Int, supervisorId: Int) {
        viewModelScope.launch {
            repository.assignSupervisor(studentId, supervisorId)
        }
    }

    fun submitReport(studentId: Int, title: String, content: String) {
        viewModelScope.launch {
            repository.insertReport(Report(studentId = studentId, title = title, content = content))
        }
    }

    fun addInternship(title: String, company: String, description: String, location: String) {
        viewModelScope.launch {
            repository.insertInternship(
                Internship(title = title, company = company, description = description, location = location)
            )
        }
    }

    fun getReportsForStudent(studentId: Int): StateFlow<List<Report>> {
        return repository.getReportsForStudent(studentId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun getReportsForSupervisor(supervisorId: Int): StateFlow<List<Report>> {
        return repository.getReportsForSupervisor(supervisorId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
}
