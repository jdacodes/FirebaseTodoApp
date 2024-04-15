package com.jdacodes.samplefirebaseapp.model.service

import com.jdacodes.samplefirebaseapp.model.Todo
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val todos: Flow<List<Todo>>
    suspend fun getTodo(todoId: String): Todo?
    suspend fun save(todo: Todo): String
    suspend fun update(todo: Todo)
    suspend fun delete(todoId: String)
}