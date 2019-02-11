package com.idemia.biosmart.base.bio_smart.capture

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBioMatcherHandler
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBiometricInfo
import com.morpho.mph_bio_sdk.android.sdk.msc.FaceCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.FingerCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.IBioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.ICaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoBioTraking
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureFeedbackListener
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureResultListener
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureTrackingListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 *  Capture Interactor
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureInteractor : CaptureBusinessLogic, BioCaptureFeedbackListener, BioCaptureResultListener, BioCaptureTrackingListener {
    private val worker = CaptureWorker()
    private var presenter: CapturePresentationLogic = CapturePresenter()

    private lateinit var handlerType: CaptureModels.CaptureHanlderType
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
        handlerType = request.handlerType
        val disposable = worker.createBioCaptureHandler(request)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ mCaptureHandler ->
            when (handlerType){
                CaptureModels.CaptureHanlderType.FACIAL ->
                    captureHandler = createFaceCaptureHandler(mCaptureHandler)
                CaptureModels.CaptureHanlderType.FINGERS ->
                    captureHandler = createFingersCaptureHandler(mCaptureHandler)
            }
            if(handlerType == CaptureModels.CaptureHanlderType.FACIAL) { // Start preview only for face
                captureHandler?.startPreview()  // Start preview after create handler
            }
            val response = CaptureModels.CreateCaptureHandler.Response()
            presenter.presentCreateCaptureHandler(response)
        }, { throwable ->
            val response = CaptureModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(disposable)
    }

    private fun createFaceCaptureHandler(mCaptureHandler: ICaptureHandler): FaceCaptureHandler {
        val faceCaptureHandler: FaceCaptureHandler = (mCaptureHandler as FaceCaptureHandler)
        Log.i(TAG, "Capture options: ${faceCaptureHandler.captureOptions}")
        // Capture delayed configuration disabled, to enable change [totalNumberOfCapturesBeforeDelay] > 0
        faceCaptureHandler.totalNumberOfCapturesBeforeDelay = -1 // Disabled
        faceCaptureHandler.setTimeCaptureDelay(1500)
        faceCaptureHandler.setBioCaptureResultListener(this)
        faceCaptureHandler.setBioCaptureFeedbackListener(this)
        return faceCaptureHandler
    }

    private fun createFingersCaptureHandler(mCaptureHandler: ICaptureHandler): FingerCaptureHandler {
        val fingersCaptureHandler: FingerCaptureHandler = (mCaptureHandler as FingerCaptureHandler)
        Log.i(TAG, "Capture options: ${fingersCaptureHandler.captureOptions}")
        fingersCaptureHandler.setBioCaptureResultListener(this)
        fingersCaptureHandler.setBioCaptureFeedbackListener(this)
        fingersCaptureHandler.setBioTrackingListener(this)
        return fingersCaptureHandler
    }

    override fun createMatcherHandler(request: CaptureModels.CreateMatcherHandler.Request) {
        val disposable = worker.createMatcherHandler(request).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ matcherHandler ->
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
        captureHandler?.let { captureHandler ->
            try {
                if(captureHandler.captureStatus != CaptureHandlerStatus.PREVIEW){
                    captureHandler.startPreview()
                }
                captureHandler.startCapture()
            }catch (e: Exception){
                val response = CaptureModels.Error.Request(e)
                showError(response)
            }
            Log.i(TAG, "CAPTURE STATUS: ${captureHandler.captureStatus}")
        }
    }

    override fun stopCapture(request: CaptureModels.StopCapture.Request) {
        captureHandler?.let {
            if(it.captureStatus == CaptureHandlerStatus.CAPTURE || it.captureStatus ==  CaptureHandlerStatus.PREVIEW) {
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

    //region BIO SMART - Bio Capture Feedback listener
    override fun onCaptureInfo(bioCaptureInfo: BioCaptureInfo?, bundle: Bundle?) {
        Log.i(TAG, "onCaptureInfo: name -> ${bioCaptureInfo?.name}")
        val response = CaptureModels.CaptureInfo.Response(bioCaptureInfo, bundle)
        presenter.presentCaptureInfo(response)
    }
    //endregion

    //region BIO SMART - Bio Capture Result Listener
    override fun onCaptureFinish() {
        Log.i(TAG, "onCaptureFinish: Capture finished with status -> ${captureHandler?.captureStatus}")
        if(captureHandler?.captureStatus != CaptureHandlerStatus.STOP){
            try{
                captureHandler?.stopCapture()   // Stop Capture on finish
                Log.i(TAG, "onCaptureFinish: Capture handler status -> ${captureHandler?.captureStatus}")
                captureHandler?.stopPreview()   // Stop preview on finish

                val response = CaptureModels.CaptureFinish.Response()
                presenter.presentCaptureFinish(response)
            }catch (e: Exception){
                val response = CaptureModels.Error.Request(e)
                showError(response)
            }
        }
    }

    override fun onCaptureSuccess(morphoImages: MutableList<MorphoImage>?) {
        Log.i(TAG, "onCaptureSuccess: Capture was successfully")
        val response = CaptureModels.CaptureSuccess.Response(morphoImages)
        presenter.presentCaptureSuccess(response)
    }

    override fun onCaptureFailure(captureError: CaptureError?, biometricInfo: IBiometricInfo?, bundle: Bundle?) {
        captureError?.let { error ->
            Log.e(TAG, "onCaptureFailure: ${error.name} - ${error.ordinal}")
            val response = CaptureModels.CaptureFailure.Response(captureError, biometricInfo, bundle)
            presenter.presentCaptureFailure(response)
        }
    }
    //endregion

    //region BIO SMART - Bio Tracking Listener
    override fun onTracking(p0: MutableList<MorphoBioTraking>?) {
        Log.i(TAG, "onTracking Biometric Location: ${p0?.first()?.biometricLocation}" )
        Log.i(TAG, "onTracking: Rect: ${p0?.first()?.rect}" )
        Log.i(TAG, "onTracking: Preview Rect: ${p0?.first()?.previewRect}")
        Log.i(TAG, "====================")
    }
    //endregion

    override fun destroyHandlers(request: CaptureModels.DestroyHandlers.Request) {
        // Destroy capture handler and matcher handler
        Log.i(TAG, "destroyHandlers: Capture status is -> ${captureHandler?.captureStatus}")
        try {
            matcherHandler?.destroy()
            matcherHandler = null
            captureHandler?.destroy()
            captureHandler = null
        }catch (e: Exception){
            Log.e(TAG, "destroyHandlers: ${e.message} - ${e.localizedMessage}")
        }
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

    /*override fun startPreview(request: CaptureModels.StartPreview.Request) {
        AsyncTask.execute {
            Log.i(TAG, "startPreview: Running on background")
            captureHandler?.startPreview()
        }
        val response = CaptureModels.StartPreview.Response()
        presenter.presentStartPreview(response)
    }*/

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
    //fun startPreview(request: CaptureModels.StartPreview.Request)

    // Show Error
    fun showError(request: CaptureModels.Error.Request)
}
