package com.idemia.biosmart.scenes.face_info

/**
 *  FaceInfo Worker
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoWorker {
    fun shouldDisplayTutorial(request: FaceInfoModels.DisplayThisTutorial.Request): Boolean {
        return request.activity.preferenceManager.getBoolean("IDEMIA_KEY_DO_NOT_SHOW_FACE_TUTORIAL", false)
    }

    fun setDisplayThisTutorial(request: FaceInfoModels.SetDisplayThisTutorial.Request){
        request.activity.preferenceManager.edit().putBoolean("IDEMIA_KEY_DO_NOT_SHOW_FACE_TUTORIAL", request.doNotShowTutorial).apply()
    }
}