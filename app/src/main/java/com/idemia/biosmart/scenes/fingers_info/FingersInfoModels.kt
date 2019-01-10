package com.idemia.biosmart.scenes.fingers_info;

import com.idemia.biosmart.base.android.BaseActivity

/**
 *  FingersInfo Models
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoModels {
    // Go To next Scene
    class GoToNextScene {
        class Request
        class Response
        class ViewModel
    }

    // Display This Tutorial
    class DisplayThisTutorial {
        data class Request(val activity: BaseActivity)
        data class Response(val doNotShowAgain: Boolean)
        data class ViewModel(val doNotShowAgain: Boolean)
    }

    // Set Display This Tutorial
    class SetDisplayThisTutorial{
        data class Request(val activity: BaseActivity, val doNotShowTutorial: Boolean)
        class Response(val doNotShowAgain: Boolean)
        class ViewModel(val doNotShowAgain: Boolean)
    }
}