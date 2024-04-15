package com.jdacodes.samplefirebaseapp.screens.todos

enum class TodoActionOption(val title: String) {
    EditTodo("Edit todo"),
    ToggleFlag("Toggle flag"),
    DeleteTodo("Delete todo");

    companion object {
        fun getByTitle(title: String): TodoActionOption {
            entries.forEach { action -> if (title == action.title) return action }
            return EditTodo
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { todoAction ->
                if (hasEditOption || todoAction != EditTodo) {
                    options.add(todoAction.title)
                }
            }
            return options
        }
    }
}