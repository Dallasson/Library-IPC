package com.app.ipc_library

import android.net.Uri

interface ApiController {


    fun sendJsonFile(uri : Uri?)
    fun sendJsonApi()
    fun sendEditText(text : String)


}