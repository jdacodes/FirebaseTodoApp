package com.jdacodes.samplefirebaseapp.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.jdacodes.samplefirebaseapp.SPLASH_SCREEN
import com.jdacodes.samplefirebaseapp.TODOS_SCREEN
import com.jdacodes.samplefirebaseapp.model.service.AccountService
import com.jdacodes.samplefirebaseapp.model.service.ConfigurationService
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.screens.SampleFirebaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : SampleFirebaseViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(TODOS_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(TODOS_SCREEN, SPLASH_SCREEN)
        }
    }
}