package com.idemia.biosmart.models

enum class AppErrorCodes(val description: String, val code: Int) {
    UNHANDLED_ERROR("This error has not been handled by App", -1),
    OK("Operation OK", 0),
    NETWORK_CONNECTION_ERROR("Verify if your device network is enabled, or Is your URL correct (ip address, port, is https)?",2),

    // WS RESPONSE CODE
    MIDDLEWARE_URL_CANNOT_BE_REACHED("Middleware URL cannot be reached",404),
}