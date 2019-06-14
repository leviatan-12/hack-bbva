package com.idemia.biosmart.scenes.fingers_info;

import com.idemia.morphobiosmart.android.BaseActivity

/**
 *  FingersInfo Models
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoModels {

    //region Go To next Scene
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

    //region Set Capture Hands
    class SetCaptureHands {
        data class Request(val activity: BaseActivity, val captureLeftHand: Boolean, val captureRightHand: Boolean)
        data class Response(val captureLeftHand: Boolean, val captureRightHand: Boolean)
        data class ViewModel(val captureLeftHand: Boolean, val captureRightHand: Boolean)
    }
    //endregion
}