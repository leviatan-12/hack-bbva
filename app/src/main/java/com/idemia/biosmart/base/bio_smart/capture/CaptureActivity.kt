package com.idemia.biosmart.base.bio_smart.capture

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.base.android.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.morpho.mph_bio_sdk.android.sdk.msc.data.CaptureOptions
import morpho.urt.msc.mscengine.MorphoSurfaceView
import java.lang.Exception

/**
 *  Capture Activity
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
abstract class CaptureActivity : BaseActivity(), CaptureDisplayLogic {

    companion object {
        private const val TAG = "CaptureActivity"
    }

    private lateinit var interactor: CaptureBusinessLogic    // Interactor
    private lateinit var router: CaptureRoutingLogic         // Router

    /**
     * Time before start capture: By default use 5 seconds,
     * this should be modified in app settings
     */
    protected var timeBeforeStartCapture = 5

    //region BASE ACTIVITY - A "Dependency Injection"
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

    //region BASE ACTIVITY - On Load Activity (called within "onCreate() method")
    override fun onLoadActivity(savedInstanceState: Bundle?) {
        try {
            val surfaceViewResource = surfaceViewLayout()
            surfaceView = findViewById(surfaceViewResource)
        } catch (e: Exception) {
            Log.e(TAG, "Error: ", e)
        }
    }
    //endregion

    //region CAPTURE ACTIVITY - Morpho Finger Capture Variables
    private lateinit var surfaceView: MorphoSurfaceView                           // Morpho surface view is "the surface" where preview displays
    private lateinit var captureOptions: CaptureOptions                          // Used to set capture options like capture mode, timeout, etc...
    private var appCaptureOptions: CaptureModels.AppCaptureOptions? = null        // To store local capture options
    //endregion

    //region CAPTURE ACTIVITY - Mandatory methods to implement
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

    /**
     * Select capture handler type to cast
     */
    protected abstract val handlerType: CaptureModels.CaptureHanlderType
    //endregion

    //region UI - Listener for permission
    private val listener = object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            Log.i(TAG, "onPermissionGranted")
            // 1.- Request for capturing Options
            requestCaptureOptions()
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: MutableList<PermissionRequest>?,
            token: PermissionToken?) {
            permissions?.forEach {permission ->
                Toast.makeText(
                    applicationContext,
                    "Permission ${permission.name} was denied. To enable please go to Applications and allow camera permissions.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    //endregion

    //region ANDROID - onResume
    override fun onResume() {
        super.onResume()
        Dexter.withActivity(this@CaptureActivity)
            .withPermissions(arrayListOf(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .withListener(listener)
            .withErrorListener { error ->
                Log.e(TAG, "Error with camera permission: ${error.name}")
            }.check()
    }
    //endregion

    //region ANDROID - onDestroy
    override fun onDestroy() {
        destroyHandlers()           // Destroy handlers
        surfaceView.onDestroy()     // Destroy surface view
        super.onDestroy()
    }
    //endregion

    //region USE CASE - Read Preferences
    private fun readPreferences() {
        val request = CaptureModels.ReadPreferences.Request(this@CaptureActivity, handlerType)
        interactor.readPreferences(request)
    }

    override fun displayReadPreferences(viewModel: CaptureModels.ReadPreferences.ViewModel) {
        appCaptureOptions = viewModel.appCaptureOptions
        timeBeforeStartCapture = viewModel.timeBeforeStartCapture
    }
    //endregion

    //region USE CASE - Request for capture options
    open fun requestCaptureOptions() {
        // Read capturing options from settings
        readPreferences()
        appCaptureOptions?.let {
            val request = CaptureModels.RequestCaptureOptions.Request(it, handlerType)
            interactor.requestCaptureOptions(request)
        }
    }

    override fun displayCaptureOptions(viewModel: CaptureModels.RequestCaptureOptions.ViewModel) {
        captureOptions = viewModel.options

        // 2.- Create Capture Handler
        createCaptureHandler()
    }
    //endregion

    //region USE CASE - Create Capture Handler
    private fun createCaptureHandler() {
        val request = CaptureModels.CreateCaptureHandler.Request(handlerType, this@CaptureActivity, captureOptions)
        interactor.createCaptureHandler(request)
    }

    override fun displayCreateCaptureHandler(viewModel: CaptureModels.CreateCaptureHandler.ViewModel) {
        // 3.- Create Matcher Handler
        createMatcherHandler()
    }
    //endregion

    //region USE CASE - Create Matcher Handler
    private fun createMatcherHandler() {
        val request = CaptureModels.CreateMatcherHandler.Request(this@CaptureActivity)
        interactor.createMatcherHandler(request)
    }

    override fun displayCreateMatcherHandler(viewModel: CaptureModels.CreateMatcherHandler.ViewModel) {
        // 4.- Ready for capture
        readyForCapture()
    }
    //endregion

    //region USE CASE - Start Capture
    /**
     * Use this method to start a new capture
     */
    protected fun startCapture() {
        val request = CaptureModels.StartCapture.Request()
        interactor.startCapture(request)
    }
    //endregion}

    //region USE CASE - Stop Capture
    /**
     * Use this method to stop a capture
     */
    protected fun stopCapture() {
        val request = CaptureModels.StopCapture.Request()
        interactor.stopCapture(request)
    }
    //endregion

    //region USE CASE - Destroy Handlers
    private fun destroyHandlers() {
        Log.i(TAG, "destroyHandlers()")
        val request = CaptureModels.DestroyHandlers.Request()
        interactor.destroyHandlers(request)
    }

    override fun displayDestroyHandlers(viewModel: CaptureModels.DestroyHandlers.ViewModel) {
        Log.i(TAG, "Handlers destroyed!")
    }
    //endregion

    //region USE CASE - Switch camera
    protected fun switchCamera() {
        val request = CaptureModels.SwitchCamera.Request()
        interactor.switchCamera(request)
    }

    override fun displaySwitchCamera(viewModel: CaptureModels.SwitchCamera.ViewModel) {
        if (viewModel.isFront)
            Log.i(TAG, "Camera switched to front")
        else
            Log.i(TAG, "Camera switched to rear")
    }
    //endregion

    //region USE CASE - Use Torch
    protected fun useTorch() {
        Log.i(TAG, "useTorch()")
        val request = CaptureModels.UseTorch.Request()
        interactor.useTorch(request)
    }

    abstract override fun displayUseTorch(viewModel: CaptureModels.UseTorch.ViewModel)
    //endregion

    //region USE CASE - Start Preview (Will be deleted)
    /*protected fun startPreview(){
        val request = CaptureModels.StartPreview.Request()
        interactor.startPreview(request)
    }*/

    override fun displayStartPreview(viewModel: CaptureModels.StartPreview.ViewModel) {
        Log.i(TAG, "displayStartPreview()")
    }
    //endregion

    //region LISTENER - BioCaptureFeedbackListener
    abstract override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel)
    //endregion

    //region LISTENER - BioCaptureResultListener
    abstract override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel)

    abstract override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel)
    abstract override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel)
    //endregion

    //region LISTENER - Display Error
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
    // Read Preferences
    fun displayReadPreferences(viewModel: CaptureModels.ReadPreferences.ViewModel)

    // Request for capture options
    fun displayCaptureOptions(viewModel: CaptureModels.RequestCaptureOptions.ViewModel)

    // Create capture handler
    fun displayCreateCaptureHandler(viewModel: CaptureModels.CreateCaptureHandler.ViewModel)

    // Create matcher handler
    fun displayCreateMatcherHandler(viewModel: CaptureModels.CreateMatcherHandler.ViewModel)

    // Destroy handlers
    fun displayDestroyHandlers(viewModel: CaptureModels.DestroyHandlers.ViewModel)

    // Switch camera
    fun displaySwitchCamera(viewModel: CaptureModels.SwitchCamera.ViewModel)

    // Use Torch
    fun displayUseTorch(viewModel: CaptureModels.UseTorch.ViewModel)

    // Start Preview
    fun displayStartPreview(viewModel: CaptureModels.StartPreview.ViewModel)

    // BioCapture feedback listener - Capture info
    fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel)

    // BioCapture Result
    fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel)

    fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel)
    fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel)

    // Display error
    fun displayError(viewModel: CaptureModels.Error.ViewModel)
}