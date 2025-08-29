package com.app.ipc_library

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.os.RemoteCallbackList
import android.util.Log
import com.app.common.IJsonCallback
import com.app.common.IJsonTransfer
import org.json.JSONException
import org.json.JSONObject

import java.io.FileInputStream


class IpcService : Service() {

    private val callbacks = RemoteCallbackList<IJsonCallback>()

    private val binder = object : IJsonTransfer.Stub() {
        override fun sendJson(jsonData: String?) {
            if (jsonData == null) return
            MessageStore.messageLiveData.postValue("Received JSON: $jsonData")

            val n = callbacks.beginBroadcast()
            for (i in 0 until n) {
                try {
                    callbacks.getBroadcastItem(i).onJsonReceived(jsonData)
                } catch (e: Exception) {
                    Log.e("SenderApp", "Callback failed", e)
                }
            }
            callbacks.finishBroadcast()
        }

        override fun sendFile(fd: ParcelFileDescriptor?) {
            if (fd == null) return
            try {
                FileInputStream(fd.fileDescriptor).use { input ->
                    val content = input.bufferedReader().readText()
                    try {
                        val jsonObject = JSONObject(content)
                        Log.d("SenderApp", "📩 Decoded JSON: $jsonObject")
                        val text = "Received JSON from File:\n$jsonObject"
                        MessageStore.messageLiveData.postValue(text)

                        val n = callbacks.beginBroadcast()
                        for (i in 0 until n) {
                            try {
                                callbacks.getBroadcastItem(i).onJsonReceived(content)
                            } catch (e: Exception) {
                                Log.e("SenderApp", "Callback failed", e)
                            }
                        }
                        callbacks.finishBroadcast()
                    } catch (je: JSONException) {
                        Log.e("SenderApp", "Invalid JSON in file", je)
                    }
                }
            } catch (e: Exception) {
                Log.e("SenderApp", "Error reading file", e)
            }
        }

        override fun registerCallback(cb: IJsonCallback?) {
            cb?.let { callbacks.register(it) }
        }

        override fun unregisterCallback(cb: IJsonCallback?) {
            cb?.let { callbacks.unregister(it) }
        }
    }

    override fun onBind(intent: Intent): IBinder = binder
}
