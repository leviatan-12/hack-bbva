package com.idemia.biosmart.scenes.authenticate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.scenes.user_info.UserInfoActivity
import com.idemia.biosmart.utils.Validator
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.activity_authenticate.*

/**
 *  Authenticate Activity
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticateActivity : BaseActivity(), AuthenticateDisplayLogic {
    private lateinit var interactor: AuthenticateBusinessLogic    // Interactor
    private lateinit var router: AuthenticateRoutingLogic         // Router

    // To check if data is completed
    private var usernameIsValid = false
    //TODO: Change to false (true it's just for testing)
    private var faceTaken = true
    private var fingersTaken = false

    companion object { private val TAG = "AuthenticateActivity" }

    override fun resourceLayoutId(): Int  = R.layout.activity_authenticate
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    //region BASE ACTIVITY - On load activity
    override fun onLoadActivity(savedInstanceState: Bundle?) {
        addObservableToUsernameTextField()
        addActionButtons()
    }
    //endregion

    //region ANDROID - On Resume
    override fun onResume() {
        super.onResume()
        addObservableToUsernameTextField()
    }
    //endregion

    //region BASE ACTIVITY - A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = AuthenticateInteractor()
        this.router = AuthenticateRouter()
        val presenter = AuthenticatePresenter()
        (this.interactor as AuthenticateInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as AuthenticateRouter).setActivity(this)
    }
    //endregion

    //region Go to next scene
    /**
     * Go To Next Scene
     */
    private fun goToNextScene(operation: AuthenticateModels.Operation) {
        val request = AuthenticateModels.GoToNextScene.Request(operation)
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: AuthenticateModels.GoToNextScene.ViewModel) {
        when(viewModel.operation){
            AuthenticateModels.Operation.START_PROCESS ->
                router.routeToStartProcessScene(UserInfoActivity.AUTHENTICATE_USER, edit_text_username.text.toString())
            AuthenticateModels.Operation.CAPTURE_FACE -> router.routeToCaptureFaceScene()
            AuthenticateModels.Operation.CAPTURE_FINGERS -> router.routeToCaptureFingersMsoScene()
            AuthenticateModels.Operation.CAPTURE_FINGERS_CONTACTLESS -> router.routeToCaptureFingersScene()
        }
    }
    //endregion

    //region UI
    /**
     * Adds an observable for username validation
     */
    private fun addObservableToUsernameTextField(){
        DisposableManager.add(edit_text_username.textChanges().subscribe{
            usernameIsValid = Validator.validateUsername(edit_text_username)
            button_start_process.isEnabled = isDataValid()
        })
    }

    /**
     * Adds on click listener to buttons
     */
    private fun addActionButtons(){
        button_start_process.setOnClickListener {
            if(isDataValid()){
                goToNextScene(AuthenticateModels.Operation.START_PROCESS)
            }
        }
        float_button_selfie.setOnClickListener{ goToNextScene(AuthenticateModels.Operation.CAPTURE_FACE) }
        button_capture_fingers.setOnClickListener {
            if(switch_enable_contactless.isChecked){
                goToNextScene(AuthenticateModels.Operation.CAPTURE_FINGERS_CONTACTLESS)
            }else{
                goToNextScene(AuthenticateModels.Operation.CAPTURE_FINGERS)
            }
        }
    }

    private fun isDataValid(): Boolean{
        return (usernameIsValid && faceTaken) || (usernameIsValid && fingersTaken)
    }
    //endregion

    //region Android - On Activity resut
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            Log.i(TAG, "Request code was $requestCode")
            when(resultCode){
                AuthenticateModels.RequestCode.REQUEST_CODE_FACE.ordinal -> faceTaken = true
                AuthenticateModels.RequestCode.REQUEST_CODE_HAND_LETT.ordinal,
                AuthenticateModels.RequestCode.REQUEST_CODE_HAND_RIGHT.ordinal -> fingersTaken = true
            }
        }else{
            Log.e(TAG, "Activity result: canceled")
        }
    }
    //endregion
}

/**
 *  Authenticate Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface AuthenticateDisplayLogic {
    fun displayGoToNextScene(viewModel: AuthenticateModels.GoToNextScene.ViewModel)
}