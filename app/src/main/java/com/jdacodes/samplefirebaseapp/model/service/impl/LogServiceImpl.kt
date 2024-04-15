package com.jdacodes.samplefirebaseapp.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.jdacodes.samplefirebaseapp.model.service.LogService
import javax.inject.Inject

class LogServiceImpl @Inject constructor(): LogService {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}