package com.idemia.biosmart.base.fingers

import com.idemia.biosmart.base.DisposableManager
import io.reactivex.disposables.Disposable

/**
 *  Fingers Interactor
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersInteractor : FingersBusinessLogic {
    private val worker = FingersWorker()
    private var presenter: FingersPresentationLogic = FingersPresenter()
    private var dispoable: Disposable? = null

    fun setPresenter(presenter: FingersPresentationLogic) {
        this.presenter = presenter
    }

    override fun requestForCapturingOptions(request: FingersModels.RequestForCaptureOptions.Request) {
        val response = FingersModels.RequestForCaptureOptions.Response(request.options)
        presenter.presentRequestForCapturingOptions(response)
    }

    override fun createCaptureHandler(request: FingersModels.CreateCaptureHandler.Request) {
        dispoable = worker.createBioCaptureHandler(request).subscribe ({ captureHandler ->
            val response = FingersModels.CreateCaptureHandler.Response(captureHandler)
            presenter.presentCreateCaptureHandler(response)
        }, { throwable ->
            val response = FingersModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(dispoable)
    }

    override fun createMatcherHandler(request: FingersModels.CreateMatcherHandler.Request) {
        dispoable = worker.createMatcherHandler(request).subscribe({ matcherHandler ->
            val response = FingersModels.CreateMatcherHandler.Response(matcherHandler)
            presenter.presentCreateMatcherHandler(response)
        },{ throwable ->
            val response = FingersModels.Error.Response(throwable)
            presenter.presentError(response)
        })
        DisposableManager.add(dispoable)
    }

    override fun showError(request: FingersModels.Error.Request) {
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
    // Request for capturing options
    fun requestForCapturingOptions(request: FingersModels.RequestForCaptureOptions.Request)

    // Create Capture Handler
    fun createCaptureHandler(request: FingersModels.CreateCaptureHandler.Request)

    // Create Matcher Handler
    fun createMatcherHandler(request: FingersModels.CreateMatcherHandler.Request)

    fun showError(request: FingersModels.Error.Request)
}