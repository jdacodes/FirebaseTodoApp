package com.jdacodes.samplefirebaseapp.screens.edit_task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.jdacodes.samplefirebaseapp.TODO_ID
import com.jdacodes.samplefirebaseapp.core.ext.idFromParameter
import com.jdacodes.samplefirebaseapp.model.Todo
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.model.service.StorageService
import com.jdacodes.samplefirebaseapp.screens.SampleFirebaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
) : SampleFirebaseViewModel(logService) {

    val todo = mutableStateOf(Todo())

    init {
        val todoId = savedStateHandle.get<String>(TODO_ID)
        if (todoId != null) {
            launchCatching {
                Log.d("EditTaskViewModel", "todoId: $todoId")
                todo.value = storageService.getTodo(todoId.idFromParameter()) ?: Todo()
                Log.d("EditTaskViewModel", "todoId: ${todoId.idFromParameter()}")
            }
        }
    }

    fun onTitleChange(newValue: String) {
        todo.value = todo.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        todo.value = todo.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        todo.value = todo.value.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        todo.value = todo.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        todo.value = todo.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        todo.value = todo.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        todo.value = todo.value.copy(priority = newValue)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedTask = todo.value
            if (editedTask.id.isBlank()) {
                storageService.save(editedTask)
            } else {
                storageService.update(editedTask)
            }
            popUpScreen()
        }
    }


    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
