package com.idemia.biosmart.scenes.enrolment

import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import kotlinx.android.synthetic.main.activity_enrolment.*

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
        button_start_process.setOnClickListener { goToNextScene(EnrolmentModels.Operation.START_PROCESS) }
        float_button_selfie.setOnClickListener{ goToNextScene(EnrolmentModels.Operation.CAPTURE_FACE) }
        button_capture_fingers.setOnClickListener {
            if(switch_enable_contactless.isChecked){
                goToNextScene(EnrolmentModels.Operation.CAPTURE_FINGERS_CONTACTLESS)
            }else{
                goToNextScene(EnrolmentModels.Operation.CAPTURE_FINGERS)
            }
        }
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
     * Go To Next Scene
     */
    private fun goToNextScene(operation: EnrolmentModels.Operation) {
        val request = EnrolmentModels.GoToNextScene.Request(operation)
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: EnrolmentModels.GoToNextScene.ViewModel) {
        when(viewModel.operation){
            EnrolmentModels.Operation.START_PROCESS -> router.routeToStartProcessScene()
            EnrolmentModels.Operation.CAPTURE_FACE -> router.routeToCaptureFaceScene()
            EnrolmentModels.Operation.CAPTURE_FINGERS -> router.routeToCaptureFingersMsoScene()
            EnrolmentModels.Operation.CAPTURE_FINGERS_CONTACTLESS -> router.routeToCaptureFingersScene()
        }
    }
}

/**
 *  Enrolment Display Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentDisplayLogic {
    fun displayGoToNextScene(viewModel: EnrolmentModels.GoToNextScene.ViewModel)
}