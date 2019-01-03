package com.idemia.biosmart.base.bio_smart.fingers


/**
 *  Fingers Presenter
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersPresenter : FingersPresentationLogic {
    private var activity: FingersDisplayLogic? = null

    fun setActivity(activity: FingersDisplayLogic) {
        this.activity = activity
    }

    companion object {
        private val TAG = "FingersPresenter"
    }


    override fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response) {
        val viewModel = FingersModels.CreateMatcherHandler.ViewModel()
        activity!!.displayCreateMatcherHandler(viewModel)
    }


    override fun presentDestroyHandlers(response: FingersModels.DestroyHandlers.Response) {
        val viewModel = FingersModels.DestroyHandlers.ViewModel()
        activity!!.displayDestroyHandlers(viewModel)
    }

    override fun presentError(response: FingersModels.Error.Response) {
        val viewModel = FingersModels.Error.ViewModel(response.throwable)
        // activity!!.displayError(viewModel)
    }
}


/**
 *  Fingers Presentation Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersPresentationLogic {
    fun presentCreateMatcherHandler(response: FingersModels.CreateMatcherHandler.Response)

    fun presentDestroyHandlers(response: FingersModels.DestroyHandlers.Response)

    fun presentError(response: FingersModels.Error.Response)
}