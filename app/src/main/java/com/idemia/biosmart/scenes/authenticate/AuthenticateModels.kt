package com.idemia.biosmart.scenes.authenticate;

/**
 *  Authenticate Models
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticateModels {

    enum class Operation {
        CAPTURE_FINGERS,
        CAPTURE_FINGERS_CONTACTLESS,
        CAPTURE_FACE,
        START_PROCESS
    }

    // Go to next scene
    class GoToNextScene {
        data class Request(val operation: AuthenticateModels.Operation)
        class Response(val operation: AuthenticateModels.Operation)
        class ViewModel(val operation: AuthenticateModels.Operation)
    }
}