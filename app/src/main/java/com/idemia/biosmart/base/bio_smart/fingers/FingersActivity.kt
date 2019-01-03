package com.idemia.biosmart.base.bio_smart.fingers

import com.idemia.biosmart.base.bio_smart.capture.CaptureActivity
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels

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

    // Fingers Capture Handler
    override val handlerType: CaptureModels.CaptureHanlderType = CaptureModels.CaptureHanlderType.FINGERS

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
}

/**
 *  Fingers Display Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersDisplayLogic