package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternshipListingsScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val internships by viewModel.allInternships.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.internship_listings)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
        ) {
            items(internships) { internship ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.padding_small)),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
                ) {
                    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                        Text(text = internship.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = internship.company, style = MaterialTheme.typography.titleMedium)
                        Text(text = internship.location, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                        Text(text = internship.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
