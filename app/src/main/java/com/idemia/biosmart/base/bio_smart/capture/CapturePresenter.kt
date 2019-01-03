package com.idemia.biosmart.base.bio_smart.capture

import com.idemia.biosmart.base.bio_smart.fingers.FingersModels
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
     */
    override fun presentReadPreferences(response: CaptureModels.ReadPreferences.Response) {
        val useCameraRear = if (response.values[0] as Boolean) Camera.REAR else Camera.FRONT
        val useTorch = if (response.values[1] as Boolean) Torch.ON else Torch.OFF
        val useOverlay = if (response.values[2] as Boolean) Overlay.ON else Overlay.OFF
        val captureTimeout = response.values[3] as Long

        val appCaptureOptions = CaptureModels.AppCaptureOptions(
            useCameraRear, useTorch,
            BioCaptureMode.FINGERPRINT_LEFT_HAND, captureTimeout, useOverlay
        )
        val viewModel = CaptureModels.ReadPreferences.ViewModel(appCaptureOptions)
        activity!!.displayReadPreferences(viewModel)
    }

    override fun presentRequestCaptureOptions(response: CaptureModels.RequestCaptureOptions.Response) {
        val capturingOptions: ICaptureOptions = CaptureOptions()
        capturingOptions.bioCaptureMode = response.options.captureMode
        capturingOptions.camera = response.options.camera
        capturingOptions.torch = response.options.torch
        capturingOptions.captureTimeout = response.options.timeout
        capturingOptions.captureImageTimeout = response.options.timeout
        capturingOptions.overlay = response.options.overlay
        val viewModel = CaptureModels.RequestCaptureOptions.ViewModel(capturingOptions)
        activity!!.displayCaptureOptions(viewModel)
    }

    override fun presentCreateCaptureHandler(response: CaptureModels.CreateCaptureHandler.Response) {
        val viewModel = CaptureModels.CreateCaptureHandler.ViewModel()
        activity!!.displayCreateCaptureHandler(viewModel)
    }

    override fun presentError(response: FingersModels.Error.Response) {
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

    fun presentError(response: FingersModels.Error.Response)
}