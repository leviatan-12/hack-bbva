package com.idemia.biosmart.base.fingers

import android.app.Activity
import com.idemia.biosmart.base.android.BaseActivity
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import java.lang.Exception

/**
 *  Fingers Models
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersModels {

    //region Custom Models
    class AppCapturingOptions(val camera: Camera,
                              val torch: Torch,
                              val captureMode: BioCaptureMode,
                              val timeout: Long = 30,
                              val overlay: Overlay = Overlay.ON)
    //endregion

    //region Use Cases

    // Read Preferences
    class ReadPreferences{
        data class Request(val activity: BaseActivity)
        class Response(val values: List<Any>)
        data class ViewModel(val appCapturingOptions: AppCapturingOptions)
    }

    // Request for capturing options
    class RequestForCaptureOptions{
        data class Request(val options: AppCapturingOptions)
        data class Response(val options: AppCapturingOptions)
        data class ViewModel(val options: ICaptureOptions)
    }

    // Create Capture Handler
    class CreateCaptureHandler{
        data class Request(val activity: Activity, val captureOptions: ICaptureOptions)
        class Response
        class ViewModel
    }

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