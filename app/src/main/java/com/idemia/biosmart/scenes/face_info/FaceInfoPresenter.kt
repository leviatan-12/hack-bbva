package com.idemia.biosmart.scenes.face_info

/**
 *  FaceInfo Presenter
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoPresenter : FaceInfoPresentationLogic {
    private var activity: FaceInfoDisplayLogic? = null

    fun setActivity(activity: FaceInfoDisplayLogic) {
        this.activity = activity
    }

    override fun presentSetDisplayThisTutorial(response: FaceInfoModels.SetDisplayThisTutorial.Response) {
        val viewModel = FaceInfoModels.SetDisplayThisTutorial.ViewModel(response.doNotShowAgain)
        activity!!.displaySetDoNotShowThisTutorialAgain(viewModel)
    }

    override fun presentDisplayThisTutorial(response: FaceInfoModels.DisplayThisTutorial.Response) {
        val viewModel = FaceInfoModels.DisplayThisTutorial.ViewModel(response.doNotShowAgain)
        activity!!.displayThisTutorial(viewModel)
    }

    override fun presentGoToNextScene(response: FaceInfoModels.GoToNextScene.Response) {
        val viewModel = FaceInfoModels.GoToNextScene.ViewModel()
        activity!!.displayGoToNextScene(viewModel)
    }

    companion object {
        private val TAG = "FaceInfoPresenter"
    }
}


/**
 *  FaceInfo Presentation Logic
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FaceInfoPresentationLogic {
    fun presentSetDisplayThisTutorial(response: FaceInfoModels.SetDisplayThisTutorial.Response)
    fun presentDisplayThisTutorial(response: FaceInfoModels.DisplayThisTutorial.Response)
    fun presentGoToNextScene(response: FaceInfoModels.GoToNextScene.Response)
}