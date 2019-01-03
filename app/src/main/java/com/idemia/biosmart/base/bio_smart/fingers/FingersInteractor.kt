package com.idemia.biosmart.base.bio_smart.fingers

/**
 *  Fingers Interactor
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersInteractor : FingersBusinessLogic {
    private val worker = FingersWorker()
    private var presenter: FingersPresentationLogic = FingersPresenter()

    companion object {
        val TAG = "FingersInteractor"
    }

    fun setPresenter(presenter: FingersPresentationLogic) {
        this.presenter = presenter
    }
}


/**
 *  Fingers Business Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface FingersBusinessLogic