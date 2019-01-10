package com.idemia.biosmart.scenes.identify

/**
 *  Identify Interactor
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyInteractor : IdentifyBusinessLogic {
    private val worker = IdentifyWorker()
    private var presenter: IdentifyPresentationLogic = IdentifyPresenter()

    fun setPresenter(presenter: IdentifyPresentationLogic) {
        this.presenter = presenter
    }

    override fun goToNextScene(request: IdentifyModels.GoToNextScene.Request) {
        val response = IdentifyModels.GoToNextScene.Response(request.operation)
        presenter.presentGoToNextScene(response)
    }
}


/**
 *  Identify Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface IdentifyBusinessLogic {
    fun goToNextScene(request: IdentifyModels.GoToNextScene.Request)
}
