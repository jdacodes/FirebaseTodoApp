package com.jdacodes.samplefirebaseapp.screens.edit_task


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jdacodes.samplefirebaseapp.core.composable.ActionToolbar
import com.jdacodes.samplefirebaseapp.core.composable.BasicField
import com.jdacodes.samplefirebaseapp.core.composable.CardSelector
import com.jdacodes.samplefirebaseapp.core.composable.EditCardEditor
import com.jdacodes.samplefirebaseapp.core.composable.TimePickerDialog
import com.jdacodes.samplefirebaseapp.core.ext.card
import com.jdacodes.samplefirebaseapp.core.ext.fieldModifier
import com.jdacodes.samplefirebaseapp.core.ext.spacer
import com.jdacodes.samplefirebaseapp.core.ext.toolbarActions
import com.jdacodes.samplefirebaseapp.model.Priority
import com.jdacodes.samplefirebaseapp.model.Todo
import java.util.Calendar
import com.jdacodes.samplefirebaseapp.R.drawable as AppIcon
import com.jdacodes.samplefirebaseapp.R.string as AppText

@Composable
fun EditTodoScreen(
    popUpScreen: () -> Unit,
    viewModel: EditTodoViewModel = hiltViewModel()
) {
    val todo by viewModel.todo


    EditTodoScreenContent(
        todo = todo,
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUrlChange = viewModel::onUrlChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onPriorityChange = viewModel::onPriorityChange,
        onFlagToggle = viewModel::onFlagToggle,
//        activity = activity
    )
}

@Composable
fun EditTodoScreenContent(
    modifier: Modifier = Modifier,
    todo: Todo,
    onDoneClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolbar(
            title = AppText.edit_todo,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_check,
            endAction = { onDoneClick() }
        )

        Spacer(modifier = Modifier.spacer())

        val fieldModifier = Modifier.fieldModifier()
        BasicField(AppText.title, todo.title, onTitleChange, fieldModifier)
        BasicField(AppText.description, todo.description, onDescriptionChange, fieldModifier)
        BasicField(AppText.url, todo.url, onUrlChange, fieldModifier)

        Spacer(modifier = Modifier.spacer())
        CardEditors(todo, onDateChange, onTimeChange)
        CardSelectors(todo, onPriorityChange, onFlagToggle)

        Spacer(modifier = Modifier.spacer())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditors(
    todo: Todo,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = System.currentTimeMillis(),
        yearRange = 2000..2024
    )
    var isDatePickerExpanded by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(is24Hour = false)
    var isTimePickerExpanded by remember { mutableStateOf(false) }


    EditCardEditor(
        AppText.date, AppIcon.ic_calendar, todo.dueDate, Modifier.card(),
        onToggleState = { isDatePickerExpanded = it },
    ) {
        ShowDatePicker(onDateChange, datePickerState)
    }

    EditCardEditor(
        AppText.time, AppIcon.ic_clock, todo.dueTime, Modifier.card(),
        onToggleState = { isTimePickerExpanded = it },
    ) {
        ShowTimePicker(onTimeChange, timePickerState)
    }
}

@Composable
private fun CardSelectors(
    todo: Todo,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(todo.priority).name
    CardSelector(
        AppText.priority,
        Priority.getOptions(),
        prioritySelection,
        Modifier.card()
    ) { newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(todo.flag).name
    CardSelector(
        AppText.flag,
        EditFlagOption.getOptions(),
        flagSelection,
        Modifier.card()
    ) { newValue
        ->
        onFlagToggle(newValue)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ShowDatePicker(
    onDateChange: (Long) -> Unit,
    datePickerState: DatePickerState
) {


    // Display the DatePickerDialog
    val showDatePicker = remember { mutableStateOf(true) }

    if (showDatePicker.value) {
        DatePickerDialog(
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker.value = false
                        datePickerState.selectedDateMillis?.let { onDateChange(it) }
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = "Confirm")
                }
            },
            onDismissRequest = { /* Handle dismissal if needed */
                showDatePicker.value = false
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text(text = "Dismiss")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false, // Optional: Hide the mode toggle for a cleaner UI
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ShowTimePicker(
    onTimeChange: (Int, Int) -> Unit,
    timePickerState: TimePickerState
) {
    val showTimePicker = remember { mutableStateOf(true) }

    val selectedTime = remember { mutableStateOf("") }
    if (showTimePicker.value) {
        TimePickerDialog(
            onCancel = { showTimePicker.value = false },
            onConfirm = {
                showTimePicker.value = false
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)

                // Perform any necessary actions with the Calendar instance
                // For example, formatting the time as a string:
//                val formattedTime = getTimeFormattedString(calendar) // Implement this function for custom formatting
                // Or, sending the time to a server:
//                sendTimeToServer(calendar.timeInMillis)

                onTimeChange(
                    timePickerState.hour,
                    timePickerState.minute
                ) // Call onTimeChange with selected time
            },
            content = { displayMode ->

//                selectedTime.value = calendar.time.time.toTime()
                if (displayMode == DisplayMode.Input) {
                    TimePicker(state = timePickerState)
                } else {
                    TimeInput(state = timePickerState)
                }
            })

    }
}

