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
        data class Request(val operation: Operation)
        class Response(val operation: Operation)
        class ViewModel(val operation: Operation)
    }

    enum class Operation{
        ENROLMENT,
        AUTHENTICATION,
        IDENTIFY,
        SETTINGS
    }
}