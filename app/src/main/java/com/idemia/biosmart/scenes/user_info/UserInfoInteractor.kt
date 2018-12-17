package com.idemia.biosmart.scenes.user_info

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.lang.Error

/**
 *  UserInfo Interactor
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoInteractor : UserInfoBusinessLogic {
    var disposable: Disposable? = null
    private val worker = UserInfoWorker()
    private var presenter: UserInfoPresentationLogic = UserInfoPresenter()

    companion object {
        val TAG = "UserInfoInteractor"
    }

    fun setPresenter(presenter: UserInfoPresentationLogic) {
        this.presenter = presenter
    }

    // TODO: Change 404 WS response when user not found...
    override fun search(request: UserInfoModels.Search.Request) {
        disposable = worker.search(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                presenter.presentSearch(response.body()!!)
        }, { throwable ->
            presenter.presentError(UserInfoModels.Error.Response(throwable))
        })
    }
}

/**
 *  UserInfo Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface UserInfoBusinessLogic {
    fun search(request: UserInfoModels.Search.Request)
}
