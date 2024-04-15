package com.jdacodes.samplefirebaseapp.screens.todos

import androidx.compose.runtime.mutableStateOf
import com.jdacodes.samplefirebaseapp.EDIT_TODO_SCREEN
import com.jdacodes.samplefirebaseapp.SETTINGS_SCREEN
import com.jdacodes.samplefirebaseapp.TODO_ID
import com.jdacodes.samplefirebaseapp.model.Todo
import com.jdacodes.samplefirebaseapp.model.service.ConfigurationService
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.model.service.StorageService
import com.jdacodes.samplefirebaseapp.screens.SampleFirebaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : SampleFirebaseViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())
    val todos = storageService.todos

    fun loadTodoOptions() {
        val hasEditOptions = configurationService.isShowTodoEditButtonConfig
        options.value = TodoActionOption.getOptions(hasEditOptions)
    }

    fun onTodoCheckChange(todo: Todo) {
        launchCatching { storageService.update(todo.copy(completed = !todo.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TODO_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onTodoActionClick(openScreen: (String) -> Unit, todo: Todo, action: String) {
        when (TodoActionOption.getByTitle(action)) {
            TodoActionOption.EditTodo -> openScreen("$EDIT_TODO_SCREEN?$TODO_ID={${todo.id}}")
            TodoActionOption.ToggleFlag -> onFlagTodoClick(todo)
            TodoActionOption.DeleteTodo -> onDeleteTodoClick(todo)
        }
    }

    private fun onFlagTodoClick(todo: Todo) {
        launchCatching { storageService.update(todo.copy(flag = !todo.flag)) }
    }

    private fun onDeleteTodoClick(todo: Todo) {
        launchCatching { storageService.delete(todo.id) }
    }
}