package com.idemia.biosmart.scenes.user_info

/**
 *  UserInfo Presenter
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoPresenter : UserInfoPresentationLogic {
    private var activity: UserInfoDisplayLogic? = null

    fun setActivity(activity: UserInfoDisplayLogic) {
        this.activity = activity
    }

    override fun authenticateUser(response: UserInfoModels.AuthenticateUser.Response) {
        val viewModel = UserInfoModels.AuthenticateUser.ViewModel(response.authenticationResponse)
        activity!!.displayAuthenticateUser(viewModel)
    }

    override fun identifyUser(response: UserInfoModels.IdentifyUser.Response) {
        val viewModel = UserInfoModels.IdentifyUser.ViewModel(response.identifyResponse)
        activity!!.displayIdentifyUser(viewModel)
    }

    override fun presentSearch(response: UserInfoModels.Search.Response) {
        when(response.codeResponse){
            "USER_FOUND" -> {
                val viewModel = UserInfoModels.Search.ViewModel(true, response.searchPersonDb.user)
                activity!!.displaySearch(viewModel)
            }
            "USER_NOT_FOUND" -> {
                val viewModel = UserInfoModels.Search.ViewModel(false)
                activity!!.displaySearch(viewModel)
            }
        }
    }

    override fun presentError(response: UserInfoModels.Error.Response) {
        val viewModel = UserInfoModels.Error.ViewModel(response.throwable)
        activity!!.displayError(viewModel)
    }

    companion object {
        private val TAG = "UserInfoPresenter"
    }
}


/**
 *  UserInfo Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface UserInfoPresentationLogic {
    fun authenticateUser(response: UserInfoModels.AuthenticateUser.Response)
    fun identifyUser(response: UserInfoModels.IdentifyUser.Response)
    fun presentSearch(response: UserInfoModels.Search.Response)
    fun presentError(response: UserInfoModels.Error.Response)
}