package com.idemia.biosmart.scenes.enrolment

/**
 *  Enrolment Models
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentModels {

    enum class RequestCode(val value: Int){
        REQUEST_CODE_FACE(0x64),
        REQUEST_CODE_HAND_LEFT(0xC8),
        REQUEST_CODE_HAND_RIGHT(0x12C)
    }

    enum class Operation{
        CAPTURE_FINGERS,
        CAPTURE_FINGERS_CONTACTLESS,
        CAPTURE_FACE,
        START_PROCESS
    }

    //region Save User Info
    class SaveUserInfo {
        data class Request(val username: String, val name: String, val lastName: String, val secondLastName: String)
        class Response
        class ViewModel
    }
    //endregion

    //region Go to next scene
    class GoToNextScene {
        data class Request(val operation: EnrolmentModels.Operation)
        class Response(val operation: EnrolmentModels.Operation)
        class ViewModel(val operation: EnrolmentModels.Operation)
    }
    //endregion
}