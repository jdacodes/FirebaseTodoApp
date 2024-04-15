package com.jdacodes.samplefirebaseapp.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.jdacodes.samplefirebaseapp.model.Todo
import com.jdacodes.samplefirebaseapp.model.service.AccountService
import com.jdacodes.samplefirebaseapp.model.service.StorageService
import com.jdacodes.samplefirebaseapp.model.service.trace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val todos: Flow<List<Todo>>
        get() =
            /*
            Adds a listener based on the user.id that is equal to the userId field from
            todo document which is contained in the Todos Collection in Firestore
            New flow will be emitted  if the currentUser changes, e.a. signing out
          **/
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(TODO_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getTodo(todoId: String): Todo? =
        firestore.collection(TODO_COLLECTION).document(todoId).get().await().toObject()

    override suspend fun save(todo: Todo): String =
        trace(SAVE_TODO_TRACE) {
            val todoWithUserId = todo.copy(userId = auth.currentUserId)
            firestore.collection(TODO_COLLECTION).add(todoWithUserId).await().id
        }

    override suspend fun update(todo: Todo): Unit =
        trace(UPDATE_TODO_TRACE) {
            firestore.collection(TODO_COLLECTION).document(todo.id).set(todo).await()
        }

    override suspend fun delete(todoId: String) {
        trace(DELETE_TODO_TRACE) {
            firestore.collection(TODO_COLLECTION).document(todoId).delete().await()
        }
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val TODO_COLLECTION = "todos"
        private const val SAVE_TODO_TRACE = "saveTodo"
        private const val UPDATE_TODO_TRACE = "updateTodo"
        private const val DELETE_TODO_TRACE = "deleteTodo"
    }
}