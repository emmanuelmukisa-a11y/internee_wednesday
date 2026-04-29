package com.ndejje.internee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ndejje.internee.data.AppDatabase
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.ui.navigation.AppNavigation
import com.ndejje.internee.ui.theme.InterneeTheme
import com.ndejje.internee.ui.viewmodel.ViewModelFactory

/**
 * MainActivity serves as the entry point for the Internee application.
 * It initializes the Room database and repository, sets up the ViewModelFactory,
 * and hosts the root navigation composable which is now extracted to its own file.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize Data Layer
        val database = AppDatabase.getDatabase(this)
        val repository = AppRepository(database.appDao())
        
        // Setup ViewModel Factory
        val viewModelFactory = ViewModelFactory(repository)

        setContent {
            InterneeTheme {
                // Invoke root composable from navigation package
                AppNavigation(viewModelFactory)
            }
        }
    }
}
