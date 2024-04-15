package com.jdacodes.samplefirebaseapp.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.jdacodes.samplefirebaseapp.SETTINGS_SCREEN
import com.jdacodes.samplefirebaseapp.SIGN_UP_SCREEN
import com.jdacodes.samplefirebaseapp.core.ext.isValidEmail
import com.jdacodes.samplefirebaseapp.core.ext.isValidPassword
import com.jdacodes.samplefirebaseapp.core.ext.passwordMatches
import com.jdacodes.samplefirebaseapp.core.snackbar.SnackbarManager
import com.jdacodes.samplefirebaseapp.model.service.AccountService
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.screens.SampleFirebaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jdacodes.samplefirebaseapp.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : SampleFirebaseViewModel(logService) {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            //if there is an error this will be caught and handle the exception, will not reach next line
            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)

        }
    }
}