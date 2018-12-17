package com.idemia.biosmart.scenes.enrolment

import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity

/**
 *  Enrolment Activity
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentActivity : BaseActivity(), EnrolmentDisplayLogic {
    private lateinit var interactor: EnrolmentBusinessLogic    // Interactor
    private lateinit var router: EnrolmentRoutingLogic         // Router

    companion object {
        private val TAG = "EnrolmentActivity"
    }

    override fun resourceLayoutId(): Int = R.layout.activity_enrolment
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity() {

    }

    override fun inject() {
        val activity = this
        this.interactor = EnrolmentInteractor()
        this.router = EnrolmentRouter()
        val presenter = EnrolmentPresenter()
        (this.interactor as EnrolmentInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as EnrolmentRouter).setActivity(this)
    }

    /**
     * Do something Use Case
     */
    private fun doSomething() {
        val request = EnrolmentModels.DoSomething.Request()
        interactor!!.doSomething(request)
    }

    override fun displayDoSomething(viewModel: EnrolmentModels.DoSomething.ViewModel) {
        Log.i(TAG, "displayDoSomething: ")
        Toast.makeText(applicationContext, "Hello World from Do Something", Toast.LENGTH_LONG).show()
    }
}

/**
 *  Enrolment Display Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentDisplayLogic {
    fun displayDoSomething(viewModel: EnrolmentModels.DoSomething.ViewModel)
}