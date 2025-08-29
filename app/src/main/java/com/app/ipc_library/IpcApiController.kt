package com.app.ipc_library

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.app.common.IControl

open class IpcApiController(
    context: Context,
    targetPackage: String,
    serviceClassName: String
) : ApiController {

    private var control: IControl? = null
    private  var isBound = false

    init {
        Log.d("LIBRARI-IPC","Ipc Api Controller Triggered")
    }


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            control = IControl.Stub.asInterface(binder)
            isBound = true
            Toast.makeText(context,"Service Conencted",Toast.LENGTH_LONG).show()
            Log.d("LIBRARI-IPC","Service Bound")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("LIBRARI-IPC","Service Unbound")
            Toast.makeText(context,"Service Disconnected",Toast.LENGTH_LONG).show()
            control = null
            isBound = false
        }
    }

    init {
        val intent = Intent().apply {
            component = ComponentName(
                targetPackage,
                serviceClassName
            )
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }


    override fun enableJsonFile(shouldSendJsonFile: Boolean) {
         try {
             if (isBound) control?.enableJsonFile(shouldSendJsonFile)
             else Log.e("LIBRARI-IPC", "Service not bound yet")
         } catch (ex : Exception){
             Log.d("LIBRARI-IPC","Error Executing Command : "  + ex.message)
         }
    }

    override fun enableJsonApi(shouldSendJsonApi: Boolean) {
         try {

             if (isBound) control?.enableJsonApi(shouldSendJsonApi)
             else Log.e("LIBRARI-IPC", "Service not bound yet")
         } catch (ex : Exception){
             Log.d("LIBRARI-IPC","Error Executing Command : "  + ex.message)
         }
    }

    override fun enableEditText(shouldSendEditText: Boolean) {
        try {
            if (isBound) control?.enableEditText(shouldSendEditText)
            else Log.e("LIBRARI-IPC", "Service not bound yet")
        } catch (ex : Exception){
            Log.d("LIBRARI-IPC","Error Executing Command : "  + ex.message)
        }
    }

}
