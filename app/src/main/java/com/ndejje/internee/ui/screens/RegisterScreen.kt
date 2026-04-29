package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ndejje.internee.R
import com.ndejje.internee.ui.components.AppButton
import com.ndejje.internee.ui.components.AppPasswordField
import com.ndejje.internee.ui.components.AppTextField
import com.ndejje.internee.ui.viewmodel.AuthState
import com.ndejje.internee.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("STUDENT") }
    var expanded by remember { mutableStateOf(false) }

    val authState by viewModel.authState

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onRegisterSuccess((authState as AuthState.Success).user.role)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )

        AppTextField(value = name, onValueChange = { name = it }, labelRes = R.string.name_label)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        AppTextField(value = email, onValueChange = { email = it }, labelRes = R.string.email_label)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        AppPasswordField(value = password, onValueChange = { password = it }, labelRes = R.string.password_label)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        // Role Dropdown
        Box {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "${stringResource(R.string.role_label)}: $role")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("STUDENT") }, onClick = { role = "STUDENT"; expanded = false })
                DropdownMenuItem(text = { Text("SUPERVISOR") }, onClick = { role = "SUPERVISOR"; expanded = false })
                DropdownMenuItem(text = { Text("ADMIN") }, onClick = { role = "ADMIN"; expanded = false })
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
        }

        AppButton(
            onClick = { viewModel.register(name, email, password, role) },
            textRes = R.string.register_button,
            enabled = authState !is AuthState.Loading
        )

        TextButton(onClick = onNavigateToLogin) {
            Text(stringResource(R.string.already_account))
        }
    }
}
