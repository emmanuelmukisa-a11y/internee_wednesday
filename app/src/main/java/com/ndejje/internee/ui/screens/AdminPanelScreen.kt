package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ndejje.internee.R
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

    // Using authViewModel to access current user if needed
    val currentUser by authViewModel.currentUser

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
            Text(
                text = "Add New Internship",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            AppTextField(value = title, onValueChange = { title = it }, labelRes = R.string.report_title)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            AppTextField(value = company, onValueChange = { company = it }, labelRes = R.string.app_name)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            AppTextField(value = location, onValueChange = { location = it }, labelRes = R.string.role_label)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.description_box_height))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            AppButton(
                onClick = {
                    mainViewModel.addInternship(title, company, description, location)
                    title = ""; company = ""; description = ""; location = ""
                },
                textRes = R.string.submit_button,
                enabled = title.isNotBlank() && company.isNotBlank()
            )
        }
    }
}
