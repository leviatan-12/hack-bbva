package com.idemia.biosmart.scenes.user_info

import com.idemia.biosmart.models.AppErrorCodes

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
        when(response.errorCode){
            AppErrorCodes.UNHANDLED_ERROR.code -> {
                val viewModel = UserInfoModels.Error.ViewModel(Throwable(AppErrorCodes.UNHANDLED_ERROR.description))
                activity!!.displayError(viewModel)
            }
            AppErrorCodes.MIDDLEWARE_URL_CANNOT_BE_REACHED.code -> {
                val viewModel = UserInfoModels.Error.ViewModel(Throwable(AppErrorCodes.MIDDLEWARE_URL_CANNOT_BE_REACHED.description))
                activity!!.displayError(viewModel)
            }
            AppErrorCodes.NETWORK_CONNECTION_ERROR.code -> {
                val viewModel = UserInfoModels.Error.ViewModel(Throwable(AppErrorCodes.MIDDLEWARE_URL_CANNOT_BE_REACHED.description))
                activity!!.displayError(viewModel)
            }else -> {
                val viewModel = UserInfoModels.Error.ViewModel(Throwable("[WS Connection] Error code ${response.errorCode}"))
                activity!!.displayError(viewModel)
            }
        }
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