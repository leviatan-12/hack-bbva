package com.idemia.biosmart.scenes.enrolment

import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.utils.Validator
import com.jakewharton.rxbinding2.widget.textChanges
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

    private var isNameValid = false
    private var isLastNameValid = false
    private var isSecondLastNameValid = false
    private var isUsernameValid = false
    private var faceTaken = false
    private var fingersTaken = false

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
        addObservables()
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

    private fun addObservables(){
        DisposableManager.add(edit_text_name.textChanges().subscribe{
            Validator.validateName(edit_text_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_last_name.textChanges().subscribe{
            Validator.validateName(edit_text_last_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_m_last_name.textChanges().subscribe{
            Validator.validateName(edit_text_m_last_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_username.textChanges().subscribe{
            Validator.validateUsername(edit_text_username)
            button_start_process.isEnabled = isDataValid()
        })
    }

    private fun isDataValid(): Boolean{
        val dataInfoValid = (isUsernameValid && isLastNameValid && isSecondLastNameValid && isUsernameValid && isNameValid)
        return (dataInfoValid && faceTaken) || (dataInfoValid && fingersTaken)
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