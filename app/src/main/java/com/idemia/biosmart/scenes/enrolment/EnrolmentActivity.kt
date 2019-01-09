package com.idemia.biosmart.scenes.enrolment

import android.app.Activity
import android.content.Intent
import android.util.Log
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
    // TODO: Set to false
    private var faceTaken = true
    private var fingersTaken = false

    override fun resourceLayoutId(): Int = R.layout.activity_enrolment
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    //region Base Activity lifecycle
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
    //endregion

    override fun onResume() {
        super.onResume()
        addObservables()
    }

    //region A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = EnrolmentInteractor()
        this.router = EnrolmentRouter()
        val presenter = EnrolmentPresenter()
        (this.interactor as EnrolmentInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as EnrolmentRouter).setActivity(this)
    }
    //endregion

    //region USECASE - Save user info
    private fun saveUserInfo(){
        val username = edit_text_username.text.toString()
        val name = edit_text_name.text.toString()
        val lastName = edit_text_last_name.text.toString()
        val secondLastName = edit_text_m_last_name.text.toString()
        val request = EnrolmentModels.SaveUserInfo.Request(username, name, lastName, secondLastName)
        interactor.saveUserInfo(request)
    }

    override fun displaySaveUserInfo(viewModel: EnrolmentModels.SaveUserInfo.ViewModel) {
        Log.i(TAG, "User info saved in App Cache")
    }
    //endregion

    //region USECASE- Go to next scene
    /**
     * Go To Next Scene
     */
    private fun goToNextScene(operation: EnrolmentModels.Operation) {
        val request = EnrolmentModels.GoToNextScene.Request(operation)
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: EnrolmentModels.GoToNextScene.ViewModel) {
        when(viewModel.operation){
            EnrolmentModels.Operation.START_PROCESS -> {
                if(isDataValid()){
                    saveUserInfo()
                    router.routeToStartProcessScene()
                    finish()
                }
            }
            EnrolmentModels.Operation.CAPTURE_FACE -> router.routeToCaptureFaceScene()
            EnrolmentModels.Operation.CAPTURE_FINGERS -> router.routeToCaptureFingersMsoScene()
            EnrolmentModels.Operation.CAPTURE_FINGERS_CONTACTLESS -> router.routeToCaptureFingersScene()
        }
    }
    //endregion

    //region Local UI elements
    private fun addObservables(){
        DisposableManager.add(edit_text_name.textChanges().subscribe{
            isNameValid = Validator.validateName(edit_text_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_last_name.textChanges().subscribe{
            isLastNameValid = Validator.validateName(edit_text_last_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_m_last_name.textChanges().subscribe{
            isSecondLastNameValid = Validator.validateName(edit_text_m_last_name)
            button_start_process.isEnabled = isDataValid()
        })
        DisposableManager.add(edit_text_username.textChanges().subscribe{
            isUsernameValid = Validator.validateUsername(edit_text_username)
            button_start_process.isEnabled = isDataValid()
        })
    }

    private fun isDataValid(): Boolean{
        val dataInfoValid = (isUsernameValid && isLastNameValid && isSecondLastNameValid && isUsernameValid && isNameValid)
        return (dataInfoValid && faceTaken) || (dataInfoValid && fingersTaken)
    }
    //endregion


    //region Android - On Activity resut
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            Log.i(TAG, "Request code was $requestCode")
            when(resultCode){
                EnrolmentModels.RequestCode.REQUEST_CODE_FACE.ordinal -> faceTaken = true
                EnrolmentModels.RequestCode.REQUEST_CODE_HAND_LETT.ordinal,
                EnrolmentModels.RequestCode.REQUEST_CODE_HAND_RIGHT.ordinal -> fingersTaken = true
            }
        }else{
            Log.e(TAG, "Activity result: canceled")
        }
    }
    //endregion
}

/**
 *  Enrolment Display Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentDisplayLogic {
    fun displaySaveUserInfo(viewModel: EnrolmentModels.SaveUserInfo.ViewModel)
    fun displayGoToNextScene(viewModel: EnrolmentModels.GoToNextScene.ViewModel)
}