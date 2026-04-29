package com.ndejje.internee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ndejje.internee.ui.screens.*
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import com.ndejje.internee.ui.viewmodel.MainViewModel
import com.ndejje.internee.ui.viewmodel.ViewModelFactory

/**
 * AppNavigation manages the navigation state of the application.
 * It defines the navigation graph for all 6 core screens and handles authentication transitions.
 */
@Composable
fun AppNavigation(viewModelFactory: ViewModelFactory) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
    val mainViewModel: MainViewModel = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { role ->
                    val destination = when (role) {
                        "STUDENT" -> Screen.StudentDashboard.route
                        "SUPERVISOR" -> Screen.SupervisorDashboard.route
                        "ADMIN" -> Screen.AdminPanel.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onRegisterSuccess = { role ->
                    val destination = when (role) {
                        "STUDENT" -> Screen.StudentDashboard.route
                        "SUPERVISOR" -> Screen.SupervisorDashboard.route
                        "ADMIN" -> Screen.AdminPanel.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.StudentDashboard.route) {
            StudentDashboardScreen(
                authViewModel = authViewModel,
                mainViewModel = mainViewModel,
                onNavigateToInternships = { navController.navigate(Screen.InternshipListings.route) },
                onNavigateToSubmitReport = { navController.navigate(Screen.ReportSubmission.route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.InternshipListings.route) {
            InternshipListingsScreen(
                viewModel = mainViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.ReportSubmission.route) {
            ReportSubmissionScreen(
                authViewModel = authViewModel,
                mainViewModel = mainViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.SupervisorDashboard.route) {
            SupervisorDashboardScreen(
                authViewModel = authViewModel,
                mainViewModel = mainViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.AdminPanel.route) {
            AdminPanelScreen(
                authViewModel = authViewModel,
                mainViewModel = mainViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
