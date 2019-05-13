package com.idemia.morphobiosmart.bio_smart.face

import com.idemia.morphobiosmart.bio_smart.capture.CaptureActivity
import com.idemia.morphobiosmart.bio_smart.capture.CaptureModels

abstract class FaceCaptureActivity: CaptureActivity() {
    // Face Capture Handler
    override val handlerType: CaptureModels.CaptureHanlderType = CaptureModels.CaptureHanlderType.FACIAL
}