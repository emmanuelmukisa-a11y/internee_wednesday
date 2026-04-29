package com.ndejje.internee.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object StudentDashboard : Screen("student_dashboard")
    object InternshipListings : Screen("internship_listings")
    object ReportSubmission : Screen("report_submission")
    object SupervisorDashboard : Screen("supervisor_dashboard")
    object AdminPanel : Screen("admin_panel")
}
