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

    override fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request) {
        worker.setDisplayThisTutorial(request)
        val response = FingersInfoModels.SetDisplayThisTutorial.Response(request.doNotShowTutorial)
        presenter.presentSetDisplayThisTutorial(response)
    }

    override fun displayThisTutorial(request: FingersInfoModels.DisplayThisTutorial.Request) {
        val flag = worker.shouldDisplayTutorial(request)
        val response = FingersInfoModels.DisplayThisTutorial.Response(flag)
        presenter.presentDisplayThisTutorial(response)
    }

    override fun goToNextScene(request: FingersInfoModels.GoToNextScene.Request) {
        val response = FingersInfoModels.GoToNextScene.Response()
        presenter.presentGoToNextScene(response)
    }
}


/**
 *  FingersInfo Business Logic
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface FingersInfoBusinessLogic {
    fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request)
    fun displayThisTutorial(request: FingersInfoModels.DisplayThisTutorial.Request)
    fun goToNextScene(request: FingersInfoModels.GoToNextScene.Request)
}
