package com.idemia.biosmart.base.bio_smart.fingers

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBioMatcherHandler
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBiometricInfo
import com.morpho.mph_bio_sdk.android.sdk.msc.FingerCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.data.BioCaptureInfo
import com.morpho.mph_bio_sdk.android.sdk.msc.data.CaptureError
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureFeedbackListener
import com.morpho.mph_bio_sdk.android.sdk.msc.listeners.BioCaptureResultListener
import io.reactivex.disposables.Disposable
import java.lang.Exception

/**
 *  Fingers Interactor
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersInteractor : FingersBusinessLogic, BioCaptureFeedbackListener, BioCaptureResultListener {
    private val worker = FingersWorker()
    private var presenter: FingersPresentationLogic = FingersPresenter()
    private var disposable: Disposable? = null
    private var captureHandler: FingerCaptureHandler? = null // Capture handler used for handling capture
    private var matcherHandler: IBioMatcherHandler? = null   // A matcher handler used for local matching

    companion object {
        val TAG = "FingersInteractor"
    }

    fun setPresenter(presenter: FingersPresentationLogic) {
        this.presenter = presenter
    }


    override fun createMatcherHandler(request: FingersModels.CreateMatcherHandler.Request) {
        disposable = worker.createMatcherHandler(request).subscribe({ matcherHandler ->
            this.matcherHandler = matcherHandler
            val response = FingersModels.CreateMatcherHandler.Response()
            presenter.presentCreateMatcherHandler(response)
        },{ throwable ->
            val response = FingersModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(disposable)
    }

    override fun startCapture(request: FingersModels.StartCapture.Request) {
        captureHandler?.let {
            try {
                it.startPreview()
                it.startCapture()
            }catch (e: Exception){
                val response = FingersModels.Error.Request(e)
                showError(response)
            }
        }
    }

    override fun stopCapture(request: FingersModels.StopCapture.Request) {
        captureHandler?.let {
            try {
                it.stopCapture()
                it.stopPreview()
            }catch (e: Exception){
                val response = FingersModels.Error.Request(e)
                showError(response)
            }
        }
    }

    //region Bio Capture Feedback listener
    override fun onCaptureInfo(bioCaptureInfo: BioCaptureInfo?, bundle: Bundle?) {
        Log.i(TAG, "onCaptureInfo: name -> ${bioCaptureInfo?.name}")
    }
    //endregion

    //region Bio Capture Result Listener
    override fun onCaptureFinish() {
        Log.i(TAG, "onCaptureFinish: Capture finished")
    }

    override fun onCaptureSuccess(morphoImages: MutableList<MorphoImage>?) {
        Log.i(TAG, "onCaptureSuccess: Capture was successfully")
    }

    override fun onCaptureFailure(captureError: CaptureError?, biometricInfo: IBiometricInfo?, bundle: Bundle?) {
        Log.i(TAG, "onCaptureFailure: An error was happened")
    }
    //endregion

    override fun destroyHandlers(request: FingersModels.DestroyHandlers.Request) {
        // Dispose everything
        DisposableManager.dispose()

        // Destroy capture handler and matcher handler
        captureHandler?.destroy()
        matcherHandler?.destroy()

        val response = FingersModels.DestroyHandlers.Response()
        presenter.presentDestroyHandlers(response)
    }

    override fun showError(request: FingersModels.Error.Request) {
        Log.e(TAG, "showError: An error was happened", request.exception)
        val response = FingersModels.Error.Response(Throwable(request.exception))
        presenter.presentError(response)
    }
}


/**
 *  Fingers Business Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface FingersBusinessLogic {
    // Create Matcher Handler
    fun createMatcherHandler(request: FingersModels.CreateMatcherHandler.Request)

    // Start Capture
    fun startCapture(request: FingersModels.StartCapture.Request)

    // Stop Capture
    fun stopCapture(request: FingersModels.StopCapture.Request)

    // Destroy Handlers
    fun destroyHandlers(request: FingersModels.DestroyHandlers.Request)

    // Show Error
    fun showError(request: FingersModels.Error.Request)
}