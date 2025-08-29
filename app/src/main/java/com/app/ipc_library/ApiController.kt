package com.app.ipc_library

interface ApiController {


    fun enableJsonFile(shouldSendJsonFile : Boolean)
    fun enableJsonApi(shouldSendJsonApi : Boolean)
    fun enableEditText(shouldSendEditText : Boolean)


}