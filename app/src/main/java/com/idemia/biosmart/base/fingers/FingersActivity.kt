package com.idemia.biosmart.base.fingers

import android.util.Log
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.morpho.mph_bio_sdk.android.sdk.msc.data.BioCaptureMode
import com.morpho.mph_bio_sdk.android.sdk.msc.data.Camera
import com.morpho.mph_bio_sdk.android.sdk.msc.data.ICaptureOptions
import com.morpho.mph_bio_sdk.android.sdk.msc.data.Torch
import morpho.urt.msc.mscengine.MorphoSurfaceView
import java.lang.Exception

/**
 *  Fingers Activity
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
abstract class FingersActivity : BaseActivity(), FingersDisplayLogic {
    private lateinit var interactor: FingersBusinessLogic    // Interactor
    private lateinit var router: FingersRoutingLogic         // Router

    companion object {
        private val TAG = "FingersActivity"
    }

    //region Morpho Finger Capture Variables
    private lateinit var surfaceView: MorphoSurfaceView         // Morpho surface view is "the surface" where preview displays
    protected lateinit var captureOptions: ICaptureOptions      // Used to set capture options like capture mode, timeout, etc...
    //endregion

    //region Mandatory methods to implement
    /**
     * Use this method to select your Surface Ui
     * @return The surface ui Resource (ex: R.id.morphoSurfaceView)
     */
    protected abstract fun surfaceViewLayout(): Int

    /**
     * This method is called when capture handler and match handler was initialized successfully.
     * Now you can start your capture.
     */
    protected abstract fun readyForCapture()
    //endregion


    //region A "Dependency" Injection
    override fun inject() {
        val activity = this
        this.interactor = FingersInteractor()
        this.router = FingersRouter()
        val presenter = FingersPresenter()
        (this.interactor as FingersInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as FingersRouter).setActivity(this)
    }
    //endregion

    //region On Load Activity (called within "onCreate() method")
    override fun onLoadActivity() {
        try{
            val surfaceViewResource = surfaceViewLayout()
            surfaceView = findViewById(surfaceViewResource)
        }catch (e: Exception){
            Log.e(TAG, "Error: ")
        }
    }
    //endregion

    //region Android Lifecycle
    override fun onResume() {
        super.onResume()
        // 1.- Request for capturing Options
        requestForCapturingOptions()

        // 2.- Create Capture Handler
        createCaptureHandler()
    }

    override fun onPause() {
        super.onPause()
        destroyHandlers()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyHandlers()
        // Destroy surface view
        surfaceView.onDestroy()
    }
    //endregion

    //region Request for capturing options
    open fun requestForCapturingOptions(){
        // Request for capturing options
        // TODO: Read capturing options from settings
        val appOptions = FingersModels.AppCapturingOptions(Camera.FRONT, Torch.ON, BioCaptureMode.FINGERPRINT_LEFT_HAND, 30)
        val request = FingersModels.RequestForCaptureOptions.Request(appOptions)
        interactor.requestForCapturingOptions(request)
    }

    override fun displayCaptureOptions(viewModel: FingersModels.RequestForCaptureOptions.ViewModel) {
        captureOptions = viewModel.options
    }
    //endregion

    //region Create Capture Handler
    private fun createCaptureHandler(){
        val request = FingersModels.CreateCaptureHandler.Request(this@FingersActivity , captureOptions)
        interactor.createCaptureHandler(request)
    }

    override fun displayCreateCaptureHandler(viewModel: FingersModels.CreateCaptureHandler.ViewModel) {
        // 3.- Create Matcher Handler
        createMatcherHandler()
    }
    //endregion

    //region Create Matcher Handler
    private fun createMatcherHandler(){
        val request = FingersModels.CreateMatcherHandler.Request(this@FingersActivity)
        interactor.createMatcherHandler(request)
    }

    override fun displayCreateMatcherHandler(viewModel: FingersModels.CreateMatcherHandler.ViewModel) {
        // 4.- Ready for capture
        readyForCapture()
    }
    //endregion

    //region Start Capture
    /**
     * Use this method to start a new capture
     */
    protected fun startCapture(){
        val request = FingersModels.StartCapture.Request()
        interactor.startCapture(request)
    }
    //endregion}

    //region Stop Capture
    /**
     * Use this method to stop a capture
     */
    protected fun stopCapture(){
        val request = FingersModels.StopCapture.Request()
        interactor.stopCapture(request)
    }
    //endregion

    //region Display Error
    abstract override fun displayError(viewModel: FingersModels.Error.ViewModel)
    //endregion

    //region Destroy Handlers
    private fun destroyHandlers(){
        DisposableManager.dispose()
    }
    //endregion
}

/**
 *  Fingers Display Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersDisplayLogic {
    fun displayCaptureOptions(viewModel: FingersModels.RequestForCaptureOptions.ViewModel)

    fun displayCreateCaptureHandler(viewModel: FingersModels.CreateCaptureHandler.ViewModel)

    fun displayCreateMatcherHandler(viewModel: FingersModels.CreateMatcherHandler.ViewModel)

    fun displayError(viewModel: FingersModels.Error.ViewModel)
}