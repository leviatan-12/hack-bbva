package com.idemia.biosmart.scenes.welcome;

/**
 *  Welcome Models
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeModels {
    // Generate License use case
    class GenerateLicense {
        class Request
        data class Response(var generated: Boolean)
        data class ViewModel(var generated: Boolean = false)
    }

    // Start enrolment usecase
    class StartEnrollment {
        class Request
        class Response
        class ViewModel
    }

    // Hello World use case
    object HelloWorld {
        class Request
        data class Response(val success: Boolean = true, val message:String)
        data class ViewModel(val available: Boolean, val message: String)
    }
}