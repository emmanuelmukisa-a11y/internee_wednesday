package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import com.ndejje.internee.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    onNavigateToInternships: () -> Unit,
    onNavigateToSubmitReport: () -> Unit,
    onLogout: () -> Unit
) {
    val user by authViewModel.currentUser
    val reports by mainViewModel.getReportsForStudent(user?.id ?: 0).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.student_dashboard)) },
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onNavigateToInternships) {
                    Text(stringResource(R.string.internship_listings))
                }
                Button(onClick = onNavigateToSubmitReport) {
                    Text(stringResource(R.string.report_submission))
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "Your Reports",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

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
                            Text(text = report.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = report.content, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
