package com.idemia.biosmart.scenes.enrolment;

/**
 *  Enrolment Models
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentModels {

    enum class Operation{
        CAPTURE_FINGERS,
        CAPTURE_FINGERS_CONTACTLESS,
        CAPTURE_FACE,
        START_PROCESS
    }

    // Go to next scene
    class GoToNextScene {
        data class Request(val operation: EnrolmentModels.Operation)
        class Response(val operation: EnrolmentModels.Operation)
        class ViewModel(val operation: EnrolmentModels.Operation)
    }
}