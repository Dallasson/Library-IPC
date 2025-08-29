package com.app.ipc_library

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.app.common.IJsonTransfer
import com.app.ipc_library.api.JsonRepository
import okhttp3.internal.readFieldOrNull
import java.io.File

open class IpcApiController(
    private var context: Context,
    private var targetPackage: String,
    private var fileProviderPackage : String,
    serviceClassName: String
) : ApiController {

    private var jsonRepository: JsonRepository = JsonRepository()
    private var jsonService: IJsonTransfer? = null


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            jsonService = IJsonTransfer.Stub.asInterface(service)
            Log.d("AppA", "Connected to App B service")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            jsonService = null
        }
    }

    init {
        val intent = Intent().apply {
            component = ComponentName(
                targetPackage,
                serviceClassName
            )
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

    }

    override fun sendJsonFile(uri: Uri?) {
        val inputStream = context.contentResolver.openInputStream(uri!!)
        val tempFile = File(context.cacheDir, "ipc_temp.json")
        inputStream.use { input -> tempFile.outputStream().use { it.write(input!!.readBytes()) } }


        val contentUri = FileProvider.getUriForFile(
            context,
            "${fileProviderPackage}.fileprovider",
            tempFile
        )

        val pfd = context.contentResolver.openFileDescriptor(contentUri, "r")
        if (pfd != null) {
            jsonService?.sendFile(pfd)
        }
    }

    override fun sendJsonApi() {
        jsonRepository.fetchAPost { posts, _ ->
            posts?.forEach { post ->
                if(post.id == 1){
                    val jsonData = """{"user":"Api","msg":"$post"}"""
                    jsonService?.sendJson(jsonData)
                }
            }
        }
    }

    override fun sendEditText(text: String) {
        val jsonData = """{"user":"Bot","msg":"$text"}"""
        jsonService?.sendJson(jsonData)
    }


}
