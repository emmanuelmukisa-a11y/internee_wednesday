package com.ndejje.internee.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.data.Internship
import com.ndejje.internee.data.Report
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    val allInternships: StateFlow<List<Internship>> = repository.getAllInternships()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allReports: StateFlow<List<Report>> = repository.getAllReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
}
