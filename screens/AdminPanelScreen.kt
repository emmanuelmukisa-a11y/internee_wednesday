package com.ndejje.internee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.data.User
import com.ndejje.internee.ui.components.AppButton
import com.ndejje.internee.ui.components.AppTextField
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import com.ndejje.internee.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    onLogout: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val students by mainViewModel.allStudents.collectAsState(initial = emptyList())
    val supervisors by mainViewModel.allSupervisors.collectAsState(initial = emptyList())

    var selectedStudent by remember { mutableStateOf<User?>(null) }
    var showAssignDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.admin_panel)) },
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
            // Section 1: Add Internship
            Text(
                text = "Add New Internship",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            AppTextField(value = title, onValueChange = { title = it }, labelRes = R.string.internship_title_label)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            AppTextField(value = company, onValueChange = { company = it }, labelRes = R.string.company_name_label)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            
            AppButton(
                onClick = {
                    mainViewModel.addInternship(title, company, description, location)
                    title = ""; company = ""
                },
                textRes = R.string.submit_button,
                enabled = title.isNotBlank() && company.isNotBlank()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            // Section 2: Assign Supervisor
            Text(
                text = "Assign Supervisors to Students",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(students) { student ->
                    val supervisor = supervisors.find { it.id == student.supervisorId }
                    ListItem(
                        headlineContent = { Text(student.name) },
                        supportingContent = { 
                            Text("Current Supervisor: ${supervisor?.name ?: "None"}") 
                        },
                        trailingContent = {
                            Button(onClick = {
                                selectedStudent = student
                                showAssignDialog = true
                            }) {
                                Text("Assign")
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }

    if (showAssignDialog && selectedStudent != null) {
        AlertDialog(
            onDismissRequest = { showAssignDialog = false },
            title = { Text("Select Supervisor for ${selectedStudent?.name}") },
            text = {
                LazyColumn {
                    items(supervisors) { supervisor ->
                        Text(
                            text = supervisor.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    mainViewModel.assignSupervisor(selectedStudent!!.id, supervisor.id)
                                    showAssignDialog = false
                                }
                                .padding(dimensionResource(R.dimen.padding_medium))
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAssignDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
