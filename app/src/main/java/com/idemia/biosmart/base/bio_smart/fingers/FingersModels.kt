package com.idemia.biosmart.base.bio_smart.fingers

import android.app.Activity
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import java.lang.Exception

/**
 *  Fingers Models
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersModels {
    //region Use Cases

    // CreateMatcherHandler
    class CreateMatcherHandler{
        class Request(val activity: Activity)
        class Response
        class ViewModel
    }

    // Start Capture
    class StartCapture{
        class Request
        class Response
        class ViewModel
    }

    // Stop Capture
    class StopCapture{
        class Request
        class Response
        class ViewModel
    }

    class DestroyHandlers{
        class Request
        class Response
        class ViewModel
    }

    // Error
    class Error {
        class Request(val exception: Exception)
        class Response(val throwable: Throwable)
        class ViewModel(val throwable: Throwable)
    }

    //endregion
}