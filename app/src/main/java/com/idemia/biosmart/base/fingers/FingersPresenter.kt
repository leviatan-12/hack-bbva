package com.idemia.biosmart.base.fingers

import com.morpho.mph_bio_sdk.android.sdk.msc.data.CaptureOptions
import com.morpho.mph_bio_sdk.android.sdk.msc.data.ICaptureOptions

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

    override fun presentRequestForCapturingOptions(response: FingersModels.RequestForCaptureOptions.Response) {
        val capturingOptions: ICaptureOptions = CaptureOptions()
        capturingOptions.bioCaptureMode = response.options.captureMode
        capturingOptions.camera = response.options.camera
        capturingOptions.torch = response.options.torch
        capturingOptions.captureTimeout = response.options.timeout
        capturingOptions.captureImageTimeout = response.options.timeout
        val viewModel = FingersModels.RequestForCaptureOptions.ViewModel(capturingOptions)
        activity!!.displayCaptureOptions(viewModel)
    }

    override fun presentCreateCaptureHandler(response: FingersModels.CreateCaptureHandler.Response) {
        val viewModel = FingersModels.CreateCaptureHandler.ViewModel(response.capturehandler)
        activity!!.displayCreateCaptureHandler(viewModel)
    }

    override fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response) {
        val viewModel = FingersModels.CreateMatcherHandler.ViewModel(response.matcherHandler)
        activity!!.displayCreateMatcherHandler(viewModel)
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
    fun presentRequestForCapturingOptions(response: FingersModels.RequestForCaptureOptions.Response)

    fun presentCreateCaptureHandler(response: FingersModels.CreateCaptureHandler.Response)

    fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response)

    fun presentError(response: FingersModels.Error.Response)
}