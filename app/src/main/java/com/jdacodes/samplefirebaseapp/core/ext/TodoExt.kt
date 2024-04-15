package com.jdacodes.samplefirebaseapp.core.ext

import com.jdacodes.samplefirebaseapp.model.Todo

fun Todo?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Todo?.hasDueTime(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}