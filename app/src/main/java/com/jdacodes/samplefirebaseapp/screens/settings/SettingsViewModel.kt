package com.jdacodes.samplefirebaseapp.screens.settings

import com.jdacodes.samplefirebaseapp.LOGIN_SCREEN
import com.jdacodes.samplefirebaseapp.SIGN_UP_SCREEN
import com.jdacodes.samplefirebaseapp.SPLASH_SCREEN
import com.jdacodes.samplefirebaseapp.model.service.AccountService
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.model.service.StorageService
import com.jdacodes.samplefirebaseapp.screens.SampleFirebaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : SampleFirebaseViewModel(logService) {
    val uiState = accountService.currentUser.map {
        SettingsUiState(it.isAnonymous)
    }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}