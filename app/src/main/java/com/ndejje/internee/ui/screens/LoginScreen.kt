package com.ndejje.internee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ndejje.internee.R
import com.ndejje.internee.ui.components.AppButton
import com.ndejje.internee.ui.components.AppPasswordField
import com.ndejje.internee.ui.components.AppTextField
import com.ndejje.internee.ui.viewmodel.AuthState
import com.ndejje.internee.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess((authState as AuthState.Success).user.role)
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
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )

        AppTextField(
            value = email,
            onValueChange = { email = it },
            labelRes = R.string.email_label
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        AppPasswordField(
            value = password,
            onValueChange = { password = it },
            labelRes = R.string.password_label
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
        }

        AppButton(
            onClick = { viewModel.login(email, password) },
            textRes = R.string.login_button,
            enabled = authState !is AuthState.Loading
        )

        TextButton(onClick = onNavigateToRegister) {
            Text(stringResource(R.string.no_account))
        }
    }
}
