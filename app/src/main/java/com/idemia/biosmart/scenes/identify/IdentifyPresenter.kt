package com.idemia.biosmart.scenes.identify

/**
 *  Identify Presenter
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyPresenter : IdentifyPresentationLogic {
    private var activity: IdentifyDisplayLogic? = null

    companion object {
        private val TAG = "IdentifyPresenter"
    }

    fun setActivity(activity: IdentifyDisplayLogic) {
        this.activity = activity
    }

    override fun presentGoToNextScene(response: IdentifyModels.GoToNextScene.Response) {
        val viewModel = IdentifyModels.GoToNextScene.ViewModel(response.operation)
        activity!!.displayGoToNextScene(viewModel)
    }
}

/**
 *  Identify Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyPresentationLogic {
    fun presentGoToNextScene(response: IdentifyModels.GoToNextScene.Response)
}