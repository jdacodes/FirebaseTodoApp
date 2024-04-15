package com.jdacodes.samplefirebaseapp.screens.todos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jdacodes.samplefirebaseapp.core.composable.ActionToolbar
import com.jdacodes.samplefirebaseapp.core.ext.smallSpacer
import com.jdacodes.samplefirebaseapp.core.ext.toolbarActions
import com.jdacodes.samplefirebaseapp.model.Todo
import com.jdacodes.samplefirebaseapp.ui.theme.SampleFirebaseAppTheme
import com.jdacodes.samplefirebaseapp.R.drawable as AppIcon
import com.jdacodes.samplefirebaseapp.R.string as AppText

@Composable
fun TodosScreen(
    openScreen: (String) -> Unit,
    viewModel: TodosViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options

    TodosScreenContent(
        onAddClick = viewModel::onAddClick,
        onSettingsClick = viewModel::onSettingsClick,
        onTodoCheckChange = viewModel::onTodoCheckChange,
        onTodoActionClick = viewModel::onTodoActionClick,
        openScreen = openScreen,
        todos = todos.value,
        options = options
    )

    LaunchedEffect(viewModel) { viewModel.loadTodoOptions() }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodosScreenContent(
    modifier: Modifier = Modifier,
    onAddClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onTodoCheckChange: (Todo) -> Unit,
    onTodoActionClick: ((String) -> Unit, Todo, String) -> Unit,
    openScreen: (String) -> Unit,
    todos: List<Todo>,
    options: List<String>,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.todos,
                endActionIcon = AppIcon.ic_settings,
                endAction = { onSettingsClick(openScreen) },
                modifier = Modifier.toolbarActions()
            )
            Spacer(modifier = Modifier.smallSpacer())
            LazyColumn {
                items(todos, key = { it.id }) { todoItem ->
                    TodoItem(
                        todo = todoItem,
                        options = options,
                        onCheckChange = { onTodoCheckChange(todoItem) },
                        onActionClick = { action ->
                            onTodoActionClick(
                                openScreen,
                                todoItem,
                                action
                            )
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodosScreenPreview() {
    val todo = Todo(
        title = "Task title",
        flag = true,
        completed = true
    )
    val options = TodoActionOption.getOptions(hasEditOption = true)
    SampleFirebaseAppTheme {
        TodosScreenContent(
            onAddClick = {},
            onSettingsClick = {},
            onTodoCheckChange = {},
            onTodoActionClick = { _, _, _ -> },
            openScreen = {},
            todos = listOf(todo),
            options = options
        )
    }
}