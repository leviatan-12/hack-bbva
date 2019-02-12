package com.idemia.biosmart.scenes.face_info

/**
 *  FaceInfo Worker
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoWorker {
    val key = "IDEMIA_KEY_DO_NOT_SHOW_FACE_TUTORIAL"

    //region Should display this tutorial
    fun shouldDisplayTutorial(request: FaceInfoModels.DisplayThisTutorial.Request): Boolean {
        return request.activity.preferenceManager.getBoolean(key, false)
    }
    //endregion

    //region Set display this tutorial
    fun setDisplayThisTutorial(request: FaceInfoModels.SetDisplayThisTutorial.Request){
        request.activity.preferenceManager.edit().putBoolean(key, request.doNotShowTutorial).apply()
    }
    //endregion
}