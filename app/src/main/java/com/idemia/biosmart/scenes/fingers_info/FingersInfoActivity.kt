package com.idemia.biosmart.scenes.fingers_info

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import kotlinx.android.synthetic.main.activity_fingers_info.*

/**
 *  FingersInfo Activity
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoActivity : BaseActivity(), FingersInfoDisplayLogic {
    private lateinit var interactor: FingersInfoBusinessLogic    // Interactor
    private lateinit var router: FingersInfoRoutingLogic         // Router

    companion object {
        private val TAG = "FingersInfoActivity"
    }

    override fun resourceLayoutId(): Int = R.layout.activity_fingers_info
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        button_start_process.setOnClickListener { goToNextScene() }
        switch_dont_show_again.setOnCheckedChangeListener { _, isChecked ->
            Log.i(TAG, "IS CHECKED? $isChecked")
            setDoNotShowThisTutorialAgain(isChecked)
        }
        shouldDisplayThisTutorial()
    }

    //region BASE ACTIVITY - A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = FingersInfoInteractor()
        this.router = FingersInfoRouter()
        val presenter = FingersInfoPresenter()
        (this.interactor as FingersInfoInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as FingersInfoRouter).setActivity(this)
    }
    //endregion

    //region USECASE - Set Do not show this tutorial
    private fun setDoNotShowThisTutorialAgain(value: Boolean){
        val request = FingersInfoModels.SetDisplayThisTutorial.Request(this@FingersInfoActivity, value)
        interactor.setDisplayThisTutorial(request)
    }

    override fun displaySetDoNotShowThisTutorialAgain(viewModel: FingersInfoModels.SetDisplayThisTutorial.ViewModel) {
        Log.i(TAG, "Show this tutorial again?: ${viewModel.doNotShowAgain}")
    }
    //endregion

    //region USECASE - Display this tutorial?
    private fun shouldDisplayThisTutorial(){
        val request = FingersInfoModels.DisplayThisTutorial.Request(this@FingersInfoActivity)
        interactor.displayThisTutorial(request)
    }

    override fun displayThisTutorial(viewModel: FingersInfoModels.DisplayThisTutorial.ViewModel) {
        if(viewModel.doNotShowAgain){
            goToNextScene()
            finish()
        }
    }
    //endregion

    //region USECASE - Go to next scene
    private fun goToNextScene() {
        val request = FingersInfoModels.GoToNextScene.Request()
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: FingersInfoModels.GoToNextScene.ViewModel) {
        router.routeToNextScene()
        finish()
    }
    //endregion
}

/**
 *  FingersInfo Display Logic
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FingersInfoDisplayLogic {
    fun displaySetDoNotShowThisTutorialAgain(viewModel: FingersInfoModels.SetDisplayThisTutorial.ViewModel)
    fun displayThisTutorial(viewModel: FingersInfoModels.DisplayThisTutorial.ViewModel)
    fun displayGoToNextScene(viewModel: FingersInfoModels.GoToNextScene.ViewModel)
}