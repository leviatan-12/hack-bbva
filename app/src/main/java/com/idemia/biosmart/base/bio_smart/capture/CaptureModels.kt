package com.idemia.biosmart.base.bio_smart.capture;

import android.app.Activity
import com.idemia.biosmart.base.android.BaseActivity
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import java.lang.Exception

/**
 *  Capture Models
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureModels {

    //region Custom Models
    class AppCaptureOptions(val camera: Camera,
                            val torch: Torch,
                            val captureMode: BioCaptureMode,
                            val timeout: Long = 30,
                            val overlay: Overlay = Overlay.ON)
    //endregion

    // Read Preferences
    class ReadPreferences{
        data class Request(val activity: BaseActivity)
        class Response(val values: List<Any>)
        data class ViewModel(val appCaptureOptions: CaptureModels.AppCaptureOptions)
    }

    // Request Capture Options
    class RequestCaptureOptions{
        data class Request(val options: CaptureModels.AppCaptureOptions)
        data class Response(val options: CaptureModels.AppCaptureOptions)
        data class ViewModel(val options: ICaptureOptions)
    }

    // Create Capture Handler
    class CreateCaptureHandler{
        data class Request(val activity: Activity, val captureOptions: ICaptureOptions)
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