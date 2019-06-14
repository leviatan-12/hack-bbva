package com.idemia.biosmart.scenes.fingers_info

/**
 *  FingersInfo Presenter
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoPresenter : FingersInfoPresentationLogic {
    private var activity: FingersInfoDisplayLogic? = null

    companion object { private val TAG = "FingersInfoPresenter" }

    fun setActivity(activity: FingersInfoDisplayLogic) {
        this.activity = activity
    }

    override fun presentSetDisplayThisTutorial(response: FingersInfoModels.SetDisplayThisTutorial.Response) {
        val viewModel = FingersInfoModels.SetDisplayThisTutorial.ViewModel(response.doNotShowAgain)
        activity!!.displaySetDoNotShowThisTutorialAgain(viewModel)
    }

    override fun presentSetCaptureHands(response: FingersInfoModels.SetCaptureHands.Response) {
        val viewModel = FingersInfoModels.SetCaptureHands.ViewModel(response.captureLeftHand, response.captureRightHand)
        activity!!.displaySetCaptureHands(viewModel)
    }

    override fun presentDisplayThisTutorial(response: FingersInfoModels.DisplayThisTutorial.Response) {
        val viewModel = FingersInfoModels.DisplayThisTutorial.ViewModel(response.doNotShowAgain)
        activity!!.displayThisTutorial(viewModel)
    }

    override fun presentGoToNextScene(response: FingersInfoModels.GoToNextScene.Response) {
        val viewModel = FingersInfoModels.GoToNextScene.ViewModel()
        activity!!.displayGoToNextScene(viewModel)
    }
}


/**
 *  FingersInfo Presentation Logic
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FingersInfoPresentationLogic {
    fun presentSetDisplayThisTutorial(response: FingersInfoModels.SetDisplayThisTutorial.Response)
    fun presentSetCaptureHands(response: FingersInfoModels.SetCaptureHands.Response)
    fun presentDisplayThisTutorial(response: FingersInfoModels.DisplayThisTutorial.Response)
    fun presentGoToNextScene(response: FingersInfoModels.GoToNextScene.Response)
}