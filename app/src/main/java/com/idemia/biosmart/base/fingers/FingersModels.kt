package com.idemia.biosmart.base.fingers

import android.app.Activity
import com.morpho.mph_bio_sdk.android.sdk.msc.data.Camera
import com.morpho.mph_bio_sdk.android.sdk.msc.data.BioCaptureMode
import com.morpho.mph_bio_sdk.android.sdk.msc.data.ICaptureOptions
import com.morpho.mph_bio_sdk.android.sdk.msc.data.Torch
import java.lang.Exception

/**
 *  Fingers Models
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersModels {

    class AppCapturingOptions(val camera: Camera, val torch: Torch, val captureMode: BioCaptureMode, val timeout: Long = 30)

    // Request for capturing options
    class RequestForCaptureOptions{
        data class Request(val options: AppCapturingOptions)
        data class Response(val options: AppCapturingOptions)
        data class ViewModel(val options: ICaptureOptions)
    }

    // Create Capture Handler
    class CreateCaptureHandler{
        data class Request(val activity: Activity, val captureOptions: ICaptureOptions)
        class Response()
        class ViewModel()
    }

    // CreateMatcherHandler
    class CreateMatcherHandler{
        class Request(val activity: Activity)
        class Response
        class ViewModel
    }

    class StartCapture{
        class Request
        class Response
        class ViewModel
    }

    class StopCapture{
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
}