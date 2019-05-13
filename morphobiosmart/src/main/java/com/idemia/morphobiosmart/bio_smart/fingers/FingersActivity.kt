package com.idemia.morphobiosmart.bio_smart.fingers

import com.idemia.morphobiosmart.bio_smart.capture.CaptureActivity
import com.idemia.morphobiosmart.bio_smart.capture.CaptureModels

/**
 *  Fingers Activity
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
abstract class FingersActivity : CaptureActivity() {

    companion object {
        private val TAG = "FingersActivity"
    }

    // Fingers Capture Handler
    override val handlerType: CaptureModels.CaptureHanlderType = CaptureModels.CaptureHanlderType.FINGERS
}