package com.idemia.biosmart.scenes.identify

/**
 *  Identify Presenter
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyPresenter : IdentifyPresentationLogic {
    private var activity: IdentifyDisplayLogic? = null

    fun setActivity(activity: IdentifyDisplayLogic) {
        this.activity = activity
    }

    override fun presentDoSomething(response: IdentifyModels.DoSomething.Response) {
        val viewModel = IdentifyModels.DoSomething.ViewModel()
        activity!!.displayDoSomething(viewModel)
    }

    companion object {
        private val TAG = "IdentifyPresenter"
    }
}


/**
 *  Identify Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyPresentationLogic {
    fun presentDoSomething(response: IdentifyModels.DoSomething.Response)
}