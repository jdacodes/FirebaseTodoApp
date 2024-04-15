package com.jdacodes.samplefirebaseapp.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}