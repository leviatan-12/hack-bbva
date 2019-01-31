package com.idemia.biosmart.base.bio_smart.capture;

import android.app.Activity
import android.os.Bundle
import com.idemia.biosmart.base.android.BaseActivity
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBiometricInfo
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
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
                            val overlay: Overlay = Overlay.ON,
                            val challengeInterDelay: Int = 3000)


    enum class CaptureHanlderType {
        FACIAL,
        FINGERS
    }
    //endregion

    // Read Preferences
    class ReadPreferences {
        data class Request(val activity: BaseActivity, val handlerType: CaptureHanlderType)
        data class Response(val values: List<Any>,val timeBeforeStartCapture: Int)
        data class ViewModel(val appCaptureOptions: CaptureModels.AppCaptureOptions, val timeBeforeStartCapture: Int)
    }

    // Request Capture Options
    class RequestCaptureOptions {
        data class Request(val options: CaptureModels.AppCaptureOptions, val handlerType: CaptureModels.CaptureHanlderType)
        data class Response(val options: CaptureModels.AppCaptureOptions,  val handlerType: CaptureModels.CaptureHanlderType)
        data class ViewModel(val options: CaptureOptions)
    }

    // Create Capture Handler
    class CreateCaptureHandler {
        data class Request(val handlerType: CaptureHanlderType, val activity: Activity, val captureOptions: ICaptureOptions)
        class Response
        class ViewModel
    }

    // CreateMatcherHandler
    class CreateMatcherHandler {
        class Request(val activity: Activity)
        class Response
        class ViewModel
    }

    // Start Capture
    class StartCapture {
        class Request
        class Response
        class ViewModel
    }

    // Stop Capture
    class StopCapture {
        class Request
        class Response
        class ViewModel
    }

    // Destroy Handlers
    class DestroyHandlers {
        class Request
        class Response
        class ViewModel
    }

    // Switch Camera
    class SwitchCamera {
        class Request
        data class Response(val camera: Camera)
        data class ViewModel(val isFront: Boolean)
    }

    // Use Torch
    class UseTorch {
        class Request
        data class Response(val torch: Torch)
        data class ViewModel(val isTorchOn: Boolean)
    }

    // Start Preview
    class StartPreview {
        class Request
        class Response
        class ViewModel
    }

    // Capture Info
    class CaptureInfo {
        data class Response(val captureInfo: BioCaptureInfo?, val bundle: Bundle?)
        class ViewModel(val hasMessage: Boolean, val message: String)
    }

    // Capture Finish
    class CaptureFinish {
        class Response
        class ViewModel
    }

    class CaptureSuccess{
        data class Response(val morphoImages: MutableList<MorphoImage>?)
        data class ViewModel(val morphoImages: MutableList<MorphoImage>?)
    }

    class CaptureFailure {
        data class Response(val captureError: CaptureError?, val biometricInfo: IBiometricInfo?, val bundle: Bundle?)
        data class ViewModel(val captureError: CaptureError?, val biometricInfo: IBiometricInfo?, val bundle: Bundle?, val hasMessage: Boolean, val message: String)
    }

    // Error
    class Error {
        class Request(val exception: Exception)
        class Response(val throwable: Throwable)
        class ViewModel(val throwable: Throwable)
    }
}