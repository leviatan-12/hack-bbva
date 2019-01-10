package com.idemia.biosmart.scenes.identify;

/**
 *  Identify Models
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyModels {

    enum class RequestCode(value: Int){
        REQUEST_CODE_FACE(0x64),
        REQUEST_CODE_HAND_LETT(0xC8),
        REQUEST_CODE_HAND_RIGHT(0x12C)
    }

    enum class Operation {
        CAPTURE_FINGERS,
        CAPTURE_FINGERS_CONTACTLESS,
        CAPTURE_FACE,
        START_PROCESS
    }

    // Go to next scene
    class GoToNextScene {
        class Request(val operation: Operation)
        class Response(val operation: Operation)
        class ViewModel(val operation: Operation)
    }
}