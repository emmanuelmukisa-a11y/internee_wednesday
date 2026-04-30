package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.ui.components.AppButton
import com.ndejje.internee.ui.components.AppTextField
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import com.ndejje.internee.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportSubmissionScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val user by authViewModel.currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.report_submission)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            AppTextField(
                value = title,
                onValueChange = { title = it },
                labelRes = R.string.report_title
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(stringResource(R.string.report_content)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
            AppButton(
                onClick = {
                    mainViewModel.submitReport(user?.id ?: 0, title, content)
                    onBack()
                },
                textRes = R.string.submit_button,
                enabled = title.isNotBlank() && content.isNotBlank()
            )
        }
    }
}
