package com.idemia.biosmart.base.bio_smart.capture

import android.util.Log
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.bio_smart.fingers.FingersModels
import com.morpho.mph_bio_sdk.android.sdk.msc.data.ICaptureOptions
import morpho.urt.msc.mscengine.MorphoSurfaceView
import java.lang.Exception

/**
 *  Capture Activity
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
abstract class CaptureActivity : BaseActivity(), CaptureDisplayLogic {
    private lateinit var interactor: CaptureBusinessLogic    // Interactor
    private lateinit var router: CaptureRoutingLogic         // Router

    companion object {
        private val TAG = "CaptureActivity"
    }

    //region A "Dependency Injection"
    override fun inject() {
        val activity = this
        this.interactor = CaptureInteractor()
        this.router = CaptureRouter()
        val presenter = CapturePresenter()
        (this.interactor as CaptureInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as CaptureRouter).setActivity(this)
    }
    //endregion

    //region Morpho Finger Capture Variables
    protected lateinit var surfaceView: MorphoSurfaceView                           // Morpho surface view is "the surface" where preview displays
    protected lateinit var captureOptions: ICaptureOptions                          // Used to set capture options like capture mode, timeout, etc...
    protected var appCaptureOptions: CaptureModels.AppCaptureOptions? = null        // To store local capture options
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

    //region On Load Activity (called within "onCreate() method")
    override fun onLoadActivity() {
        try{
            val surfaceViewResource = surfaceViewLayout()
            surfaceView = findViewById(surfaceViewResource)
        }catch (e: Exception){
            Log.e(TAG, "Error: ", e)
        }
    }
    //endregion

    //region Android Lifecycle
    override fun onResume() {
        super.onResume()
        // 1.- Request for capturing Options
        requestCaptureOptions()

        // 2.- Create Capture Handler
        createCaptureHandler()
    }

    override fun onPause() {
        super.onPause()
        // - destroyHandlers()
    }

    override fun onDestroy() {
        super.onDestroy()
        // - destroyHandlers()
        // Destroy surface view
        surfaceView.onDestroy()
    }
    //endregion

    //region Read Preferences
    protected fun readPreferences(){
        val request = CaptureModels.ReadPreferences.Request(this@CaptureActivity)
        interactor.readPreferences(request)
    }

    override fun displayReadPreferences(viewModel: CaptureModels.ReadPreferences.ViewModel) {
        appCaptureOptions = viewModel.appCaptureOptions
    }
    //endregion

    //region Request for capturing options
    open fun requestCaptureOptions(){
        // Read capturing options from settings
        readPreferences()
        appCaptureOptions?.let {
            val request = CaptureModels.RequestCaptureOptions.Request(it)
            interactor.requestCaptureOptions(request)
        }
    }

    override fun displayCaptureOptions(viewModel: CaptureModels.RequestCaptureOptions.ViewModel) {
        captureOptions = viewModel.options
    }
    //endregion

    //region Create Capture Handler
    private fun createCaptureHandler(){
        val request = CaptureModels.CreateCaptureHandler.Request(this@CaptureActivity, captureOptions)
        interactor.createCaptureHandler(request)
    }

    override fun displayCreateCaptureHandler(viewModel: CaptureModels.CreateCaptureHandler.ViewModel) {
        // 3.- Create Matcher Handler
        // - createMatcherHandler()
    }
    //endregion

    //region Display Error
    abstract override fun displayError(viewModel: CaptureModels.Error.ViewModel)
    //endregion
}

/**
 *  Capture Display Logic
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface CaptureDisplayLogic {
    fun displayReadPreferences(viewModel: CaptureModels.ReadPreferences.ViewModel)

    fun displayCaptureOptions(viewModel: CaptureModels.RequestCaptureOptions.ViewModel)

    fun displayCreateCaptureHandler(viewModel: CaptureModels.CreateCaptureHandler.ViewModel)

    fun displayError(viewModel: CaptureModels.Error.ViewModel)
}