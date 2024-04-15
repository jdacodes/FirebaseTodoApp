package com.jdacodes.samplefirebaseapp.model

import com.google.firebase.firestore.DocumentId

data class Todo(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
)
