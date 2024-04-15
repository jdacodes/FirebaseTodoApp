package com.jdacodes.samplefirebaseapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdacodes.samplefirebaseapp.core.snackbar.SnackbarManager
import com.jdacodes.samplefirebaseapp.core.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.jdacodes.samplefirebaseapp.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class SampleFirebaseViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}