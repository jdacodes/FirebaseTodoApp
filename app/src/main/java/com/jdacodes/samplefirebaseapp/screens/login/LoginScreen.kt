package com.jdacodes.samplefirebaseapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jdacodes.samplefirebaseapp.core.composable.BasicButton
import com.jdacodes.samplefirebaseapp.core.composable.BasicTextButton
import com.jdacodes.samplefirebaseapp.core.composable.BasicToolbar
import com.jdacodes.samplefirebaseapp.core.composable.EmailField
import com.jdacodes.samplefirebaseapp.core.composable.PasswordField
import com.jdacodes.samplefirebaseapp.core.ext.basicButton
import com.jdacodes.samplefirebaseapp.core.ext.fieldModifier
import com.jdacodes.samplefirebaseapp.core.ext.textButton
import com.jdacodes.samplefirebaseapp.ui.theme.SampleFirebaseAppTheme
import com.jdacodes.samplefirebaseapp.R.string as AppText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    BasicToolbar(AppText.login_details)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.sign_in, Modifier.basicButton()) { onSignInClick() }

        BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
            onForgotPasswordClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val uiState = LoginUiState(
        email = "email@test.com"
    )

    SampleFirebaseAppTheme {
        LoginScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onSignInClick = { },
            onForgotPasswordClick = { }
        )
    }
}