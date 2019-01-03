package com.idemia.biosmart.base.bio_smart.fingers

import android.util.Log
import com.idemia.biosmart.base.bio_smart.capture.CaptureActivity

/**
 *  Fingers Activity
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
abstract class FingersActivity : CaptureActivity(), FingersDisplayLogic {
    private lateinit var interactor: FingersBusinessLogic    // Interactor
    private lateinit var router: FingersRoutingLogic         // Router

    companion object {
        private val TAG = "FingersActivity"
    }

    //region A "Dependency" Injection
    override fun inject() {
        super.inject()
        val activity = this
        this.interactor = FingersInteractor()
        this.router = FingersRouter()
        val presenter = FingersPresenter()
        (this.interactor as FingersInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as FingersRouter).setActivity(this)
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
    fun displayCreateMatcherHandler(viewModel: FingersModels.CreateMatcherHandler.ViewModel)

    fun displayDestroyHandlers(viewModel: FingersModels.DestroyHandlers.ViewModel)
}