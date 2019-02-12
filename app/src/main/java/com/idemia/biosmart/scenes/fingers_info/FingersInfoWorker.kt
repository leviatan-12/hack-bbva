package com.idemia.biosmart.scenes.fingers_info;

/**
 *  FingersInfo Worker
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoWorker {
    private val key = "IDEMIA_KEY_DO_NOT_SHOW_FINGERS_TUTORIAL"
    private val keyLeftHand = "IDEMIA_KEY_CAPTURE_LEFT_HAND";
    private val keyRightHand = "IDEMIA_KEY_CAPTURE_RIGHT_HAND"

    //region Should display tutorial
    fun shouldDisplayTutorial(request: FingersInfoModels.DisplayThisTutorial.Request): Boolean {
        return request.activity.preferenceManager.getBoolean(key, false)
    }
    //endregion

    //region Set display this tutorial
    fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request){
        request.activity.preferenceManager.edit().putBoolean(key , request.doNotShowTutorial).apply()
    }
    //endregion

    //region Set capture hands
    fun setCaptureHands(request: FingersInfoModels.SetCaptureHands.Request){
        request.activity.preferenceManager.edit().putBoolean(keyLeftHand, request.captureLeftHand).apply()
        request.activity.preferenceManager.edit().putBoolean(keyRightHand, request.captureRightHand).apply()
    }
    //endregion
}