package com.idemia.biosmart.base.fingers

import android.util.Log
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.morpho.mph_bio_sdk.android.sdk.msc.data.*
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

    private var appCapturingOptions: FingersModels.AppCapturingOptions? = null

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

    //region Read Preferences
    private fun readPreferences(){
        val request = FingersModels.ReadPreferences.Request(this@FingersActivity)
        interactor.readPreferences(request)
    }

    override fun displayReadPreferences(viewModel: FingersModels.ReadPreferences.ViewModel) {
        appCapturingOptions = viewModel.appCapturingOptions
    }
    //endregion

    //region Request for capturing options
    open fun requestForCapturingOptions(){
        // Read capturing options from settings
        readPreferences()
        appCapturingOptions?.let {
            val request = FingersModels.RequestForCaptureOptions.Request(it)
            interactor.requestForCapturingOptions(request)
        }
    }

    override fun displayCaptureOptions(viewModel: FingersModels.RequestForCaptureOptions.ViewModel) {
        captureOptions = viewModel.options
    }
    //endregion

    //region Create Capture Handler
    private fun createCaptureHandler(){
        val request = FingersModels.CreateCaptureHandler.Request(this, captureOptions)
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
        val request = FingersModels.DestroyHandlers.Request()
        interactor.destroyHandlers(request)
    }

    override fun displayDestroyHandlers(viewModel: FingersModels.DestroyHandlers.ViewModel) {
        Log.i(TAG, "Handlers destroyed!")
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
    fun displayReadPreferences(viewModel: FingersModels.ReadPreferences.ViewModel)

    fun displayCaptureOptions(viewModel: FingersModels.RequestForCaptureOptions.ViewModel)

    fun displayCreateCaptureHandler(viewModel: FingersModels.CreateCaptureHandler.ViewModel)

    fun displayCreateMatcherHandler(viewModel: FingersModels.CreateMatcherHandler.ViewModel)

    fun displayDestroyHandlers(viewModel: FingersModels.DestroyHandlers.ViewModel)

    fun displayError(viewModel: FingersModels.Error.ViewModel)
}