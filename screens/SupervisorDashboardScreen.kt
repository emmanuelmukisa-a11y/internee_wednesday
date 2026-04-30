package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import com.ndejje.internee.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupervisorDashboardScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    onLogout: () -> Unit
) {
    val user by authViewModel.currentUser
    // Only fetch reports for students assigned to this supervisor
    val reports by mainViewModel.getReportsForSupervisor(user?.id ?: 0).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.supervisor_dashboard)) },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = "Welcome, ${user?.name}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            Text(
                text = "Reports from Assigned Students",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            if (reports.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No reports found for your assigned students.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_medium))
                ) {
                    items(reports) { report ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(R.dimen.padding_small)),
                            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
                        ) {
                            Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                                Text(text = "Student ID: ${report.studentId}", style = MaterialTheme.typography.labelSmall)
                                Text(text = report.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                                Text(text = report.content, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
