package com.jdacodes.samplefirebaseapp.screens.sign_up

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
import com.jdacodes.samplefirebaseapp.core.composable.BasicToolbar
import com.jdacodes.samplefirebaseapp.core.composable.EmailField
import com.jdacodes.samplefirebaseapp.core.composable.PasswordField
import com.jdacodes.samplefirebaseapp.core.composable.RepeatPasswordField
import com.jdacodes.samplefirebaseapp.core.ext.basicButton
import com.jdacodes.samplefirebaseapp.core.ext.fieldModifier
import com.jdacodes.samplefirebaseapp.ui.theme.SampleFirebaseAppTheme
import com.jdacodes.samplefirebaseapp.R.string as AppText

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
    )

}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    BasicToolbar(AppText.create_account)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(value = uiState.email, onEmailChange, fieldModifier)
        PasswordField(value = uiState.password, onPasswordChange, fieldModifier)
        RepeatPasswordField(value = uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)

        BasicButton(text = AppText.create_account, Modifier.basicButton()) {
            onSignUpClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    SampleFirebaseAppTheme {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { }
        )
    }
}
