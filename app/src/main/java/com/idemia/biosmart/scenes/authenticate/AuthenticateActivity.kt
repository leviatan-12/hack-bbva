package com.idemia.biosmart.scenes.authenticate

import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity

/**
 *  Authenticate Activity
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticateActivity : BaseActivity(), AuthenticateDisplayLogic {
    private lateinit var interactor: AuthenticateBusinessLogic    // Interactor
    private lateinit var router: AuthenticateRoutingLogic         // Router

    override fun resourceLayoutId(): Int  = R.layout.activity_authenticate
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity() {

    }

    companion object {
        private val TAG = "AuthenticateActivity"
    }

    override fun inject() {
        val activity = this
        this.interactor = AuthenticateInteractor()
        this.router = AuthenticateRouter()
        val presenter = AuthenticatePresenter()
        (this.interactor as AuthenticateInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as AuthenticateRouter).setActivity(this)
    }


    /**
     * Do something Use Case
     */
    private fun doSomething() {
        val request = AuthenticateModels.DoSomething.Request()
        interactor.doSomething(request)
    }

    override fun displayDoSomething(viewModel: AuthenticateModels.DoSomething.ViewModel) {
        Log.i(TAG, "displayDoSomething: ")
        Toast.makeText(applicationContext, "Hello World from Do Something", Toast.LENGTH_LONG).show()
    }
}

/**
 *  Authenticate Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface AuthenticateDisplayLogic {
    fun displayDoSomething(viewModel: AuthenticateModels.DoSomething.ViewModel)
}