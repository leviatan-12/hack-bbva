package com.idemia.biosmart.base.bio_smart.capture

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.base.bio_smart.fingers.FingersModels
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

/**
 *  Capture Interactor
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureInteractor : CaptureBusinessLogic, BioCaptureFeedbackListener, BioCaptureResultListener {
    private val worker = CaptureWorker()
    private var presenter: CapturePresentationLogic = CapturePresenter()
    private var disposable: Disposable? = null

    private var captureHandler: FingerCaptureHandler? = null    // Capture handler used for handling capture
    private var matcherHandler: IBioMatcherHandler? = null      // A matcher handler used for local matching

    companion object {
        val TAG = "CaptureInteractor"
    }

    fun setPresenter(presenter: CapturePresentationLogic) {
        this.presenter = presenter
    }

    override fun readPreferences(request: CaptureModels.ReadPreferences.Request) {
        val values = worker.readPreferences(request)
        val response = CaptureModels.ReadPreferences.Response(values)
        presenter.presentReadPreferences(response)
    }

    override fun requestCaptureOptions(request: CaptureModels.RequestCaptureOptions.Request) {
        val response = CaptureModels.RequestCaptureOptions.Response(request.options)
        presenter.presentRequestCaptureOptions(response)
    }

    override fun createCaptureHandler(request: CaptureModels.CreateCaptureHandler.Request) {
        disposable = worker.createBioCaptureHandler(request).subscribe ({ captureHandler ->
            val mCaptureHandler = (captureHandler as FingerCaptureHandler)
            this.captureHandler = mCaptureHandler
            this.captureHandler!!.setBioCaptureResultListener(this)
            this.captureHandler!!.setBioCaptureFeedbackListener(this)
            val response = CaptureModels.CreateCaptureHandler.Response()
            presenter.presentCreateCaptureHandler(response)
        }, { throwable ->
            val response = FingersModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(disposable)
    }


    override fun onCaptureInfo(p0: BioCaptureInfo?, p1: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCaptureFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCaptureSuccess(p0: MutableList<MorphoImage>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCaptureFailure(p0: CaptureError?, p1: IBiometricInfo?, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(request: FingersModels.Error.Request) {
        Log.e(TAG, "showError: An error was happened", request.exception)
        val response = FingersModels.Error.Response(Throwable(request.exception))
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

    // Show Error
    fun showError(request: FingersModels.Error.Request)
}
