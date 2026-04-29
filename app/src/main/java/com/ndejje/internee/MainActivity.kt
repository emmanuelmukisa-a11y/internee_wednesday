package com.ndejje.internee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.ndejje.internee.data.*
import com.ndejje.internee.ui.navigation.AppNavigation
import com.ndejje.internee.ui.theme.InterneeTheme
import com.ndejje.internee.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(this)
        val repository = AppRepository(database.appDao())
        val viewModelFactory = ViewModelFactory(repository)

        lifecycleScope.launch {
            try {
                val dao = database.appDao()
                
                // 1. Force ensure test users exist
                val student = User(id = 1, name = "John Student", email = "student@example.com", password = "password", role = "STUDENT")
                val admin = User(id = 2, name = "System Admin", email = "admin@example.com", password = "admin123", role = "ADMIN")
                dao.insertUsers(listOf(student, admin))

                // 2. Populate Internships if empty
                val currentInternships = dao.getAllInternships().first()
                if (currentInternships.isEmpty()) {
                    populateInternships(dao)
                }

                // 3. Populate Reports if empty
                val currentReports = dao.getReportsForStudent(1).first()
                if (currentReports.isEmpty()) {
                    populateReports(dao)
                }
                
                Log.d("MainActivity", "Database initialized. Admin: admin@example.com / admin123")
            } catch (e: Exception) {
                Log.e("MainActivity", "Database error", e)
            }
        }

        setContent {
            InterneeTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModelFactory)
                }
            }
        }
    }

    private suspend fun populateInternships(dao: AppDao) {
        val internships = listOf(
            Internship(title = "Android Engineer", company = "Google", description = "Develop mobile apps.", location = "Mountain View, CA"),
            Internship(title = "Product Designer", company = "Airbnb", description = "Design travel experiences.", location = "San Francisco, CA"),
            Internship(title = "Kotlin Specialist", company = "JetBrains", description = "Build developer tools.", location = "Prague, CZ")
        )
        dao.insertInternships(internships)
    }

    private suspend fun populateReports(dao: AppDao) {
        val reports = (1..10).map { i ->
            Report(studentId = 1, title = "Weekly Progress #$i", content = "Everything is going well.")
        }
        dao.insertReports(reports)
    }
}
