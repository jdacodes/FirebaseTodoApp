package com.jdacodes.samplefirebaseapp.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.jdacodes.samplefirebaseapp.model.service.ConfigurationService
import javax.inject.Inject
import com.jdacodes.samplefirebaseapp.R.xml as AppConfig
import com.jdacodes.samplefirebaseapp.BuildConfig
import com.jdacodes.samplefirebaseapp.model.service.trace
import kotlinx.coroutines.tasks.await

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {
    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean {
        trace(FETCH_CONFIG_TRACE) {
            return remoteConfig.fetchAndActivate().await()
        }
    }

    override val isShowTodoEditButtonConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_todo_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}