package com.idemia.biosmart.scenes.fingers_info

/**
 *  FingersInfo Interactor
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoInteractor : FingersInfoBusinessLogic {
    private val worker = FingersInfoWorker()
    private var presenter: FingersInfoPresentationLogic = FingersInfoPresenter()

    fun setPresenter(presenter: FingersInfoPresentationLogic) {
        this.presenter = presenter
    }

    //region Set display this tutorial
    override fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request) {
        worker.setDisplayThisTutorial(request)
        val response = FingersInfoModels.SetDisplayThisTutorial.Response(request.doNotShowTutorial)
        presenter.presentSetDisplayThisTutorial(response)
    }
    //endregion

    //region Set capture hands
    override fun setCaptureHands(request: FingersInfoModels.SetCaptureHands.Request) {
        worker.setCaptureHands(request)
        val response = FingersInfoModels.SetCaptureHands.Response(request.captureLeftHand, request.captureRightHand)
        presenter.presentSetCaptureHands(response)
    }
    //endregion

    //region Display this tutorial
    override fun displayThisTutorial(request: FingersInfoModels.DisplayThisTutorial.Request) {
        val flag = worker.shouldDisplayTutorial(request)
        val response = FingersInfoModels.DisplayThisTutorial.Response(flag)
        presenter.presentDisplayThisTutorial(response)
    }
    //endregion

    //region Go to next scene
    override fun goToNextScene(request: FingersInfoModels.GoToNextScene.Request) {
        val response = FingersInfoModels.GoToNextScene.Response()
        presenter.presentGoToNextScene(response)
    }
    //endregion
}


/**
 *  FingersInfo Business Logic
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface FingersInfoBusinessLogic {
    fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request)
    fun setCaptureHands(request: FingersInfoModels.SetCaptureHands.Request)
    fun displayThisTutorial(request: FingersInfoModels.DisplayThisTutorial.Request)
    fun goToNextScene(request: FingersInfoModels.GoToNextScene.Request)
}
