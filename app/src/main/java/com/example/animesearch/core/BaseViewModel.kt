package com.example.animesearch.core

import android.util.Log
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    protected open fun processError(throwable: Throwable) {
        Log.d(this::class.java.simpleName, throwable.stackTraceToString())
    }
}