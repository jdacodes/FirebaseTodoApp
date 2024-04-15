package com.jdacodes.samplefirebaseapp.model.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
    val isShowTodoEditButtonConfig: Boolean
}