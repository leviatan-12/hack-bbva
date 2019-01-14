package com.idemia.biosmart.scenes.user_info

import android.util.Log
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
                .subscribe{ onNext ->
                    if(onNext.isSuccessful){
                        presenter.authenticateUser(UserInfoModels.AuthenticateUser.Response(onNext.body()!!))
                    }else {
                        Log.e(TAG, "authenticateUser:" + onNext.errorBody())
                        val response = UserInfoModels.Error.Response(Throwable("Communication Error"), onNext.code())
                        presenter.presentError(response)
                    }
                })
    }

    override fun identifyUser(request: UserInfoModels.IdentifyUser.Request) {
        DisposableManager.add(
            worker.identifyUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ onNext ->
                    if(onNext.isSuccessful){
                        presenter.identifyUser(UserInfoModels.IdentifyUser.Response(onNext.body()!!))
                    }else {
                        Log.e(TAG, "identifyUser:" + onNext.errorBody())
                        val response = UserInfoModels.Error.Response(Throwable("Communication Error"), onNext.code())
                        presenter.presentError(response)
                    }
                }
        )
    }

    override fun search(request: UserInfoModels.Search.Request) {
        DisposableManager.add(worker.search(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                presenter.presentSearch(response.body()!!)
            }, { throwable ->
                presenter.presentError(UserInfoModels.Error.Response(throwable))
            }))
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
