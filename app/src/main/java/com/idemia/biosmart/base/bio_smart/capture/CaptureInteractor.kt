package com.idemia.biosmart.base.bio_smart.capture

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBioMatcherHandler
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBiometricInfo
import com.morpho.mph_bio_sdk.android.sdk.msc.BioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.FaceCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.FingerCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.IBioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureFeedbackListener
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureResultListener
import java.lang.Exception

/**
 *  Capture Interactor
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureInteractor : CaptureBusinessLogic, BioCaptureFeedbackListener, BioCaptureResultListener {
    private val worker = CaptureWorker()
    private var presenter: CapturePresentationLogic = CapturePresenter()

    private var captureHandler: IBioCaptureHandler? = null    // Capture handler used for handling capture
    private var matcherHandler: IBioMatcherHandler? = null      // A matcher handler used for local matching

    companion object {
        val TAG = "CaptureInteractor"
    }

    fun setPresenter(presenter: CapturePresentationLogic) {
        this.presenter = presenter
    }

    override fun readPreferences(request: CaptureModels.ReadPreferences.Request) {
        val values = worker.readPreferences(request)
        val timeBeforeStartCapture = worker.readTimeBeforeStartCapture(request)
        val response = CaptureModels.ReadPreferences.Response(values, timeBeforeStartCapture)
        presenter.presentReadPreferences(response)
    }

    override fun requestCaptureOptions(request: CaptureModels.RequestCaptureOptions.Request) {
        val response = CaptureModels.RequestCaptureOptions.Response(request.options, request.handlerType)
        presenter.presentRequestCaptureOptions(response)
    }

    override fun createCaptureHandler(request: CaptureModels.CreateCaptureHandler.Request) {
        val disposable = worker.createBioCaptureHandler(request).subscribe ({ captureHandler ->
            var mCaptureHandler: BioCaptureHandler? = null

            when (request.handlerType){
                CaptureModels.CaptureHanlderType.FACIAL -> {
                    mCaptureHandler = (captureHandler as FaceCaptureHandler)

                    // Capture delayed configuration disabled, to enable change [totalNumberOfCapturesBeforeDelay] > 0
                    mCaptureHandler.totalNumberOfCapturesBeforeDelay = -1 // Disabled
                    mCaptureHandler.setTimeCaptureDelay(1500)
                    this.captureHandler = mCaptureHandler
                }
                CaptureModels.CaptureHanlderType.FINGERS -> {
                    mCaptureHandler = (captureHandler as FingerCaptureHandler)
                    this.captureHandler = mCaptureHandler
                }
            }
            this.captureHandler!!.setBioCaptureResultListener(this)
            this.captureHandler!!.setBioCaptureFeedbackListener(this)
            this.captureHandler!!.startPreview()    // Start preview now...
            val response = CaptureModels.CreateCaptureHandler.Response()
            presenter.presentCreateCaptureHandler(response)
        }, { throwable ->
            val response = CaptureModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(disposable)
    }

    override fun createMatcherHandler(request: CaptureModels.CreateMatcherHandler.Request) {
        val disposable = worker.createMatcherHandler(request).subscribe({ matcherHandler ->
            this.matcherHandler = matcherHandler
            val response = CaptureModels.CreateMatcherHandler.Response()
            presenter.presentCreateMatcherHandler(response)
        },{ throwable ->
            val response = CaptureModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(disposable)
    }

    override fun startCapture(request: CaptureModels.StartCapture.Request) {
        captureHandler?.let {
            try {
                if(it.captureStatus == CaptureHandlerStatus.STOP){
                    it.startPreview()
                }
                it.startCapture()
            }catch (e: Exception){
                val response = CaptureModels.Error.Request(e)
                showError(response)
            }
        }
    }

    override fun stopCapture(request: CaptureModels.StopCapture.Request) {
        captureHandler?.let {
            if(it.captureStatus == CaptureHandlerStatus.CAPTURE || it.captureStatus ==  CaptureHandlerStatus.PREVIEW){
                try {
                    it.stopCapture()
                    it.stopPreview()
                }catch (e: Exception){
                    val response = CaptureModels.Error.Request(e)
                    showError(response)
                }
            }
        }
    }

    //region Bio Capture Feedback listener
    override fun onCaptureInfo(bioCaptureInfo: BioCaptureInfo?, bundle: Bundle?) {
        Log.i(TAG, "onCaptureInfo: name -> ${bioCaptureInfo?.name}")
        val response = CaptureModels.CaptureInfo.Response(bioCaptureInfo, bundle)
        presenter.presentCaptureInfo(response)
    }
    //endregion

    //region Bio Capture Result Listener
    override fun onCaptureFinish() {
        Log.i(TAG, "onCaptureFinish: Capture finished")
        val response = CaptureModels.CaptureFinish.Response()
        presenter.presentCaptureFinish(response)
    }

    override fun onCaptureSuccess(morphoImages: MutableList<MorphoImage>?) {
        Log.i(TAG, "onCaptureSuccess: Capture was successfully")
        val response = CaptureModels.CaptureSuccess.Response(morphoImages)
        presenter.presentCaptureSuccess(response)
    }

    override fun onCaptureFailure(captureError: CaptureError?, biometricInfo: IBiometricInfo?, bundle: Bundle?) {
        Log.i(TAG, "onCaptureFailure: An error was happened")
        val response = CaptureModels.CaptureFailure.Response(captureError, biometricInfo, bundle)
        presenter.presentCaptureFailure(response)
    }
    //endregion

    override fun destroyHandlers(request: CaptureModels.DestroyHandlers.Request) {
        // Dispose everything
        DisposableManager.clear()

        // Destroy capture handler and matcher handler
        captureHandler?.destroy()
        matcherHandler?.destroy()

        val response = CaptureModels.DestroyHandlers.Response()
        presenter.presentDestroyHandlers(response)
    }

    //region Switch camera
    override fun switchCamera(request: CaptureModels.SwitchCamera.Request) {
        var camera = Camera.REAR
        when(captureHandler?.captureOptions?.camera){
            Camera.FRONT -> camera = Camera.REAR
            Camera.REAR -> camera = Camera.FRONT
        }
        captureHandler?.captureOptions?.camera = camera
        captureHandler?.switchCamera(camera)
        val response = CaptureModels.SwitchCamera.Response(camera)
        presenter.presentSwitchCamera(response)
    }
    //endregion

    //region Use Torch
    override fun useTorch(request: CaptureModels.UseTorch.Request) {
        var torch = Torch.ON
        when(captureHandler?.captureOptions?.torch){
            Torch.ON -> torch = Torch.OFF
            Torch.OFF -> torch = Torch.ON
        }
        captureHandler?.captureOptions?.torch = torch
        captureHandler?.setTorch(torch)
        val response = CaptureModels.UseTorch.Response(torch)
        presenter.presentUseTorch(response)
    }
    //endregion

    override fun startPreview(request: CaptureModels.StartPreview.Request) {
        captureHandler?.startPreview()
        val response = CaptureModels.StartPreview.Response()
        presenter.presentStartPreview(response)
    }

    override fun showError(request: CaptureModels.Error.Request) {
        Log.e(TAG, "showError: An error was happened", request.exception)
        val response = CaptureModels.Error.Response(Throwable(request.exception))
        presenter.presentError(response)
    }
}


/**
 *  Capture Business Logic
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface CaptureBusinessLogic {
    // Read Preferences
    fun readPreferences(request: CaptureModels.ReadPreferences.Request)

    // Request Capture Options
    fun requestCaptureOptions(request: CaptureModels.RequestCaptureOptions.Request)

    // Create Capture Handler
    fun createCaptureHandler(request: CaptureModels.CreateCaptureHandler.Request)

    // Create Matcher Handler
    fun createMatcherHandler(request: CaptureModels.CreateMatcherHandler.Request)

    // Start Capture
    fun startCapture(request: CaptureModels.StartCapture.Request)

    // Stop Capture
    fun stopCapture(request: CaptureModels.StopCapture.Request)

    // Destroy Handlers
    fun destroyHandlers(request: CaptureModels.DestroyHandlers.Request)

    // Switch Camera
    fun switchCamera(request: CaptureModels.SwitchCamera.Request)

    // Use Torch
    fun useTorch(request: CaptureModels.UseTorch.Request)

    // Start Preview
    fun startPreview(request: CaptureModels.StartPreview.Request)

    // Show Error
    fun showError(request: CaptureModels.Error.Request)
}
