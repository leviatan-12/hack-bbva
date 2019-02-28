package com.idemia.biosmart.scenes.face_info;

import com.idemia.morphobiosmart.android.BaseActivity

/**
 *  FaceInfo Models
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoModels {

    //region Go Next Scene
    class GoToNextScene {
        class Request
        class Response
        class ViewModel
    }
    //endregion

    //region Display This Tutorial
    class DisplayThisTutorial {
        data class Request(val activity: BaseActivity)
        data class Response(val doNotShowAgain: Boolean)
        data class ViewModel(val doNotShowAgain: Boolean)
    }
    //endregion

    //region Set Display This Tutorial
    class SetDisplayThisTutorial{
        data class Request(val activity: BaseActivity, val doNotShowTutorial: Boolean)
        class Response(val doNotShowAgain: Boolean)
        class ViewModel(val doNotShowAgain: Boolean)
    }
    //endregion
}