package com.idemia.biosmart.base.fingers

import com.morpho.mph_bio_sdk.android.sdk.msc.data.*

/**
 *  Fingers Presenter
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersPresenter : FingersPresentationLogic {
    private var activity: FingersDisplayLogic? = null

    fun setActivity(activity: FingersDisplayLogic) {
        this.activity = activity
    }

    companion object {
        private val TAG = "FingersPresenter"
    }

    /**
     * 0.- Use camera Rear (Boolean)
     * 1.- Use torch (Boolean)
     * 3.- Use Overlay (Boolean)
     * 4.- Capture timeout (Long)
     */
    override fun presentReadPreferences(response: FingersModels.ReadPreferences.Response) {
        val useCameraRear = if (response.values[0] as Boolean) Camera.REAR else Camera.FRONT
        val useTorch = if (response.values[1] as Boolean) Torch.ON else Torch.OFF
        val useOverlay = if (response.values[2] as Boolean) Overlay.ON else Overlay.OFF
        val captureTimeout = response.values[3] as Long

        val appCaptureOptions = FingersModels.AppCapturingOptions(useCameraRear, useTorch,
            BioCaptureMode.FINGERPRINT_LEFT_HAND, captureTimeout, useOverlay)
        val viewModel = FingersModels.ReadPreferences.ViewModel(appCaptureOptions)
        activity!!.displayReadPreferences(viewModel)
    }

    override fun presentRequestForCapturingOptions(response: FingersModels.RequestForCaptureOptions.Response) {
        val capturingOptions: ICaptureOptions = CaptureOptions()
        capturingOptions.bioCaptureMode = response.options.captureMode
        capturingOptions.camera = response.options.camera
        capturingOptions.torch = response.options.torch
        capturingOptions.captureTimeout = response.options.timeout
        capturingOptions.captureImageTimeout = response.options.timeout
        capturingOptions.overlay = response.options.overlay
        val viewModel = FingersModels.RequestForCaptureOptions.ViewModel(capturingOptions)
        activity!!.displayCaptureOptions(viewModel)
    }

    override fun presentCreateCaptureHandler(response: FingersModels.CreateCaptureHandler.Response) {
        val viewModel = FingersModels.CreateCaptureHandler.ViewModel()
        activity!!.displayCreateCaptureHandler(viewModel)
    }

    override fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response) {
        val viewModel = FingersModels.CreateMatcherHandler.ViewModel()
        activity!!.displayCreateMatcherHandler(viewModel)
    }


    override fun presentDestroyHandlers(response: FingersModels.DestroyHandlers.Response) {
        val viewModel = FingersModels.DestroyHandlers.ViewModel()
        activity!!.displayDestroyHandlers(viewModel)
    }

    override fun presentError(response: FingersModels.Error.Response) {
        val viewModel = FingersModels.Error.ViewModel(response.throwable)
        activity!!.displayError(viewModel)
    }
}


/**
 *  Fingers Presentation Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersPresentationLogic {
    fun presentReadPreferences(response: FingersModels.ReadPreferences.Response)

    fun presentRequestForCapturingOptions(response: FingersModels.RequestForCaptureOptions.Response)

    fun presentCreateCaptureHandler(response: FingersModels.CreateCaptureHandler.Response)

    fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response)

    fun presentDestroyHandlers(response: FingersModels.DestroyHandlers.Response)

    fun presentError(response: FingersModels.Error.Response)
}