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
        val capturingOptions: ICaptureOptions
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
        capturingOptions.captureImageTimeout = response.options.timeout
        capturingOptions.overlay = response.options.overlay
        capturingOptions.logLevel = LogLevel.DEBUG

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

    override fun presentCaptureInfo(response: CaptureModels.CaptureInfo.Response) {
        val message = response.captureInfo.toString()
        val viewModel = CaptureModels.CaptureInfo.ViewModel(message)
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
        val viewModel = CaptureModels.CaptureFailure.ViewModel(response.captureError, response.biometricInfo, response.bundle)
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

    fun presentCaptureInfo(response: CaptureModels.CaptureInfo.Response)

    fun presentCaptureFinish(response: CaptureModels.CaptureFinish.Response)

    fun presentCaptureSuccess(response: CaptureModels.CaptureSuccess.Response)

    fun presentCaptureFailure(response: CaptureModels.CaptureFailure.Response)

    fun presentError(response: CaptureModels.Error.Response)
}