package com.app.ipc_library

import androidx.lifecycle.MutableLiveData

object MessageStore {
    val messageLiveData = MutableLiveData<String>()
}