package com.idemia.biosmart.scenes.user_info

import com.idemia.biosmart.base.utils.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

    override fun authenticateUser(request: UserInfoModels.AuthenticateUser.Request) {
        DisposableManager.add(
            worker.authenticateUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    presenter.authenticateUser(UserInfoModels.AuthenticateUser.Response(response.body()!!))
                }, { t ->
                    presenter.presentError(UserInfoModels.Error.Response(t))
                })
        )
    }

    override fun identifyUser(request: UserInfoModels.IdentifyUser.Request) {
        DisposableManager.add(
            worker.identifyUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    presenter.identifyUser(UserInfoModels.IdentifyUser.Response(response.body()!!))
                }, { t ->
                    presenter.presentError(UserInfoModels.Error.Response(t))
                })
        )
    }

    // TODO: Change 404 WS response when user not found...
    override fun search(request: UserInfoModels.Search.Request) {
        disposable = worker.search(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                presenter.presentSearch(response.body()!!)
        }, { throwable ->
            presenter.presentError(UserInfoModels.Error.Response(throwable))
        })
        DisposableManager.add(disposable)
    }
}

/**
 *  UserInfo Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface UserInfoBusinessLogic {
    fun authenticateUser(request: UserInfoModels.AuthenticateUser.Request)
    fun identifyUser(request: UserInfoModels.IdentifyUser.Request)
    fun search(request: UserInfoModels.Search.Request)
}
