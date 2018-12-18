package com.idemia.biosmart.scenes.identify

import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity

/**
 *  Identify Activity
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyActivity : BaseActivity(), IdentifyDisplayLogic {
    private lateinit var interactor: IdentifyBusinessLogic    // Interactor
    private lateinit var router: IdentifyRoutingLogic         // Router

    override fun resourceLayoutId(): Int = R.layout.activity_identify
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity() {

    }

    override fun inject() {
        val activity = this
        this.interactor = IdentifyInteractor()
        this.router = IdentifyRouter()
        val presenter = IdentifyPresenter()
        (this.interactor as IdentifyInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as IdentifyRouter).setActivity(this)
    }

    companion object {
        private val TAG = "IdentifyActivity"
    }

    /**
     * Do something Use Case
     */
    private fun doSomething() {
        val request = IdentifyModels.DoSomething.Request()
        interactor.doSomething(request)
    }

    override fun displayDoSomething(viewModel: IdentifyModels.DoSomething.ViewModel) {
        Log.i(TAG, "displayDoSomething: ")
        Toast.makeText(applicationContext, "Hello World from Do Something", Toast.LENGTH_LONG).show()
    }
}

/**
 *  Identify Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyDisplayLogic {
    fun displayDoSomething(viewModel: IdentifyModels.DoSomething.ViewModel)
}