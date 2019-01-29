package com.idemia.biosmart.base.bio_smart.capture

import com.morpho.mph_bio_sdk.android.sdk.common.LogLevel
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*

/**
 *  Capture Presenter
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CapturePresenter : CapturePresentationLogic {
    private var activity: CaptureDisplayLogic? = null

    companion object {
        private val TAG = "CapturePresenter"
    }

    fun setActivity(activity: CaptureDisplayLogic) {
        this.activity = activity
    }

    /**
     * 0.- Use camera Rear (Boolean)
     * 1.- Use torch (Boolean)
     * 3.- Use Overlay (Boolean)
     * 4.- Capture timeout (Long)
     * X.- Last must be Capture Mode
     */
    override fun presentReadPreferences(response: CaptureModels.ReadPreferences.Response) {
        val useCameraRear = if (response.values[0] as Boolean) Camera.REAR else Camera.FRONT
        val useTorch = if (response.values[1] as Boolean) Torch.ON else Torch.OFF
        val useOverlay = if (response.values[2] as Boolean) Overlay.ON else Overlay.OFF
        val captureTimeout = response.values[3] as Long
        val captureMode = response.values[4] as BioCaptureMode
        val challengeInterDelay = (response.values[5] as String).toInt()

        val appCaptureOptions = CaptureModels.AppCaptureOptions(
            useCameraRear,
            useTorch,
            captureMode,
            captureTimeout,
            useOverlay,
            challengeInterDelay
        )

        val viewModel = CaptureModels.ReadPreferences.ViewModel(appCaptureOptions, response.timeBeforeStartCapture)
        activity!!.displayReadPreferences(viewModel)
    }

    override fun presentRequestCaptureOptions(response: CaptureModels.RequestCaptureOptions.Response) {
        var capturingOptions: ICaptureOptions
        if(response.handlerType == CaptureModels.CaptureHanlderType.FACIAL){
            capturingOptions = FaceCaptureOptions()
            capturingOptions.challengeInterDelay = response.options.challengeInterDelay
            capturingOptions.camera = Camera.FRONT
        }else {
            capturingOptions  = FingerCaptureOptions()
            capturingOptions.camera = Camera.REAR
        }
        capturingOptions.bioCaptureMode = response.options.captureMode
        capturingOptions.torch = response.options.torch
        capturingOptions.captureTimeout = response.options.timeout
        capturingOptions.captureImageTimeout = -1
        capturingOptions.overlay = response.options.overlay
        capturingOptions.logLevel = LogLevel.DISABLE

        val viewModel = CaptureModels.RequestCaptureOptions.ViewModel(capturingOptions)
        activity!!.displayCaptureOptions(viewModel)
    }

    override fun presentCreateCaptureHandler(response: CaptureModels.CreateCaptureHandler.Response) {
        val viewModel = CaptureModels.CreateCaptureHandler.ViewModel()
        activity!!.displayCreateCaptureHandler(viewModel)
    }


    override fun presentCreateMatcherHandler(response: CaptureModels.CreateMatcherHandler.Response) {
        val viewModel = CaptureModels.CreateMatcherHandler.ViewModel()
        activity!!.displayCreateMatcherHandler(viewModel)
    }

    override fun presentDestroyHandlers(response: CaptureModels.DestroyHandlers.Response) {
        val viewModel = CaptureModels.DestroyHandlers.ViewModel()
        activity!!.displayDestroyHandlers(viewModel)
    }

    override fun presentSwitchCamera(response: CaptureModels.SwitchCamera.Response) {
        var isFront = true
        if(response.camera == Camera.REAR)
            isFront = false
        val viewModel = CaptureModels.SwitchCamera.ViewModel(isFront)
        activity!!.displaySwitchCamera(viewModel)
    }

    override fun presentUseTorch(response: CaptureModels.UseTorch.Response) {
        var isTorchOn = false
        if(response.torch == Torch.ON)
            isTorchOn = true
        val viewModel = CaptureModels.UseTorch.ViewModel(isTorchOn)
        activity!!.displayUseTorch(viewModel)
    }

    override fun presentStartPreview(response: CaptureModels.StartPreview.Response) {
        val vm = CaptureModels.StartPreview.ViewModel()
        activity!!.displayStartPreview(vm)
    }

    override fun presentCaptureInfo(response: CaptureModels.CaptureInfo.Response) {
        // Message pasring info
        var message = response.captureInfo.toString()
        var hasCustomMessage = true
        when(response.captureInfo){
            BioCaptureInfo.FACE_INFO_GET_OUT_FIELD -> message = "Please move out to the camera"
            BioCaptureInfo.FACE_INFO_COME_BACK_FIELD -> message = "Please move into the camera"
            BioCaptureInfo.FACE_INFO_TURN_LEFT -> message = "Turn head left"
            BioCaptureInfo.FACE_INFO_TURN_RIGHT -> message = "Turn head right"
            BioCaptureInfo.FACE_INFO_TURN_DOWN -> message = "Turn head down"
            BioCaptureInfo.FACE_INFO_CENTER_LOOK_FRONT_OF_CAMERA -> message = "Please look in front of the camera"
            BioCaptureInfo.FACE_INFO_CENTER_LOOK_CAMERA_WITH_LESS_MOVEMENT -> message = "Please look at the camera with less movement"
            BioCaptureInfo.FACE_INFO_TURN_LEFTRIGHT -> message = "Turn your head left to right or right to left"
            else -> hasCustomMessage = false
        }
        val viewModel = CaptureModels.CaptureInfo.ViewModel(hasCustomMessage, message)
        activity!!.displayCaptureInfo(viewModel)
    }

    override fun presentCaptureFinish(response: CaptureModels.CaptureFinish.Response) {
        val viewModel = CaptureModels.CaptureFinish.ViewModel()
        activity!!.displayCaptureFinish(viewModel)
    }

    override fun presentCaptureSuccess(response: CaptureModels.CaptureSuccess.Response) {
        val viewModel = CaptureModels.CaptureSuccess.ViewModel(response.morphoImages)
        activity!!.displayCaptureSuccess(viewModel)
    }

    override fun presentCaptureFailure(response: CaptureModels.CaptureFailure.Response) {
        var hasMessage = true
        var message = ""
        when(response.captureError){
            CaptureError.UNKNOWN -> message = "Unknown Error!"
            CaptureError.LOW_RESOLUTION -> message = "Low resolution"
            CaptureError.TOO_FAST -> message = "Capture too fast"
            CaptureError.HINT_UNKNOWN -> message = "Hit value is unknown"
            CaptureError.CAPTURE_TIMEOUT -> message = "Capture timeout!"
            CaptureError.CAPTURE_DELAYED ->  message = "Capture delayed due to liveness challenge failures"
            CaptureError.BAD_CAPTURE -> message = "Bad capture"
            CaptureError.BAD_CAPTURE_FINGERS -> message = "Capture of the fingers went wrong!"
            CaptureError.BAD_CAPTURE_FACE -> message = "Capture if the face went wrong!"
            CaptureError.BAD_CAPTURE_HAND -> message = "Capture of the hand went wrong!"
            CaptureError.LIVENESS_CHECK -> message = "Liveness check has failed"
            else -> hasMessage = false
        }
        val viewModel = CaptureModels.CaptureFailure.ViewModel(response.captureError, response.biometricInfo, response.bundle, hasMessage, message)
        activity!!.displayCaptureFailure(viewModel)
    }

    override fun presentError(response: CaptureModels.Error.Response) {
        val viewModel = CaptureModels.Error.ViewModel(response.throwable)
        activity!!.displayError(viewModel)
    }
}


/**
 *  Capture Presentation Logic
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface CapturePresentationLogic {
    fun presentReadPreferences(response: CaptureModels.ReadPreferences.Response)

    fun presentRequestCaptureOptions(response: CaptureModels.RequestCaptureOptions.Response)

    fun presentCreateCaptureHandler(response: CaptureModels.CreateCaptureHandler.Response)

    fun presentCreateMatcherHandler(response: CaptureModels.CreateMatcherHandler.Response)

    fun presentDestroyHandlers(response: CaptureModels.DestroyHandlers.Response)

    fun presentSwitchCamera(response: CaptureModels.SwitchCamera.Response)

    fun presentUseTorch(response: CaptureModels.UseTorch.Response)

    fun presentStartPreview(response: CaptureModels.StartPreview.Response)

    fun presentCaptureInfo(response: CaptureModels.CaptureInfo.Response)

    fun presentCaptureFinish(response: CaptureModels.CaptureFinish.Response)

    fun presentCaptureSuccess(response: CaptureModels.CaptureSuccess.Response)

    fun presentCaptureFailure(response: CaptureModels.CaptureFailure.Response)

    fun presentError(response: CaptureModels.Error.Response)
}