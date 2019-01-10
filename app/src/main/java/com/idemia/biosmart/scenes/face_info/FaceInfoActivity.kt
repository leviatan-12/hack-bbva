package com.idemia.biosmart.scenes.face_info

import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import kotlinx.android.synthetic.main.activity_face_info.*

/**
 *  FaceInfo Activity
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoActivity : BaseActivity(), FaceInfoDisplayLogic {
    private lateinit var interactor: FaceInfoBusinessLogic    // Interactor
    private lateinit var router: FaceInfoRoutingLogic         // Router

    companion object {
        private val TAG = "FaceInfoActivity"
    }

    override fun resourceLayoutId(): Int = R.layout.activity_face_info
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

    //region A "Dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = FaceInfoInteractor()
        this.router = FaceInfoRouter()
        val presenter = FaceInfoPresenter()
        (this.interactor as FaceInfoInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as FaceInfoRouter).setActivity(this)
    }
    //endregion

    //region Set Do not show this tutorial
    private fun setDoNotShowThisTutorialAgain(value: Boolean){
        val request = FaceInfoModels.SetDisplayThisTutorial.Request(this@FaceInfoActivity, value)
        interactor.setDisplayThisTutorial(request)
    }

    override fun displaySetDoNotShowThisTutorialAgain(viewModel: FaceInfoModels.SetDisplayThisTutorial.ViewModel) {
        Log.i(TAG, "Show this tutorial again?: ${viewModel.doNotShowAgain}")
    }
    //endregion

    //region Display this tutorial?
    private fun shouldDisplayThisTutorial(){
        val request = FaceInfoModels.DisplayThisTutorial.Request(this@FaceInfoActivity)
        interactor.displayThisTutorial(request)
    }

    override fun displayThisTutorial(viewModel: FaceInfoModels.DisplayThisTutorial.ViewModel) {
        if(viewModel.doNotShowAgain){
            goToNextScene()
            finish()
        }
    }
    //endregion

    //region Go to next scene
    private fun goToNextScene() {
        val request = FaceInfoModels.GoToNextScene.Request()
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: FaceInfoModels.GoToNextScene.ViewModel) {
        router.routeToNextScene()
        finish()
    }
    //endregion
}

/**
 *  FaceInfo Display Logic
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FaceInfoDisplayLogic {
    fun displaySetDoNotShowThisTutorialAgain(viewModel: FaceInfoModels.SetDisplayThisTutorial.ViewModel)
    fun displayGoToNextScene(viewModel: FaceInfoModels.GoToNextScene.ViewModel)
    fun displayThisTutorial(viewModel: FaceInfoModels.DisplayThisTutorial.ViewModel)
}