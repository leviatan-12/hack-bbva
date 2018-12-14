package com.idemia.biosmart.scenes.welcome;

/**
 *  Welcome Models
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeModels {
    // Generate License
    class GenerateLicense {
        class Request
        class Response(var generated: Boolean)
        class ViewModel(var generated: Boolean = false)
    }

    class StartEnrollment {
        class Request
        class Response
        class ViewModel
    }
}