package com.idemia.biosmart.base.bio_smart.fingers


/**
 *  Fingers Presenter
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersPresenter : FingersPresentationLogic {
    private var activity: FingersDisplayLogic? = null

    fun setActivity(activity: FingersDisplayLogic) {
        this.activity = activity
    }

    companion object {
        private val TAG = "FingersPresenter"
    }
}


/**
 *  Fingers Presentation Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersPresentationLogic