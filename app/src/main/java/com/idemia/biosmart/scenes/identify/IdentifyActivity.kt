package com.idemia.biosmart.scenes.identify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.scenes.user_info.UserInfoActivity
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_identify.*

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

    private var subject: Subject<Boolean> = PublishSubject.create()
    private var disposable: Disposable? = null

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        float_button_selfie.setOnClickListener { goToNextScene(IdentifyModels.Operation.CAPTURE_FACE) }
        button_start_process.setOnClickListener { goToNextScene(IdentifyModels.Operation.START_PROCESS) }
        button_capture_fingers.setOnClickListener {
            if(switch_enable_contactless.isChecked){
                goToNextScene(IdentifyModels.Operation.CAPTURE_FINGERS_CONTACTLESS)
            }else{
                goToNextScene(IdentifyModels.Operation.CAPTURE_FINGERS)
            }
        }
        button_start_process.isEnabled = false
        addCaptureDoneObservable()
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

    //region ANDROID - On destroy
    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
    //endregion

    companion object {
        private val TAG = "IdentifyActivity"
    }


    //region USECASE - Go to next scene
    private fun goToNextScene(operation: IdentifyModels.Operation){
        val request = IdentifyModels.GoToNextScene.Request(operation)
        interactor.goToNextScene(request)
    }

    override fun displayGoToNextScene(viewModel: IdentifyModels.GoToNextScene.ViewModel) {
        when(viewModel.operation){
            IdentifyModels.Operation.CAPTURE_FACE -> { router.routeToCaptureFaceScene() }
            IdentifyModels.Operation.CAPTURE_FINGERS -> { router.routeToCaptureFingersMsoScene() }
            IdentifyModels.Operation.CAPTURE_FINGERS_CONTACTLESS -> { router.routeToCaptureFingersScene() }
            IdentifyModels.Operation.START_PROCESS -> { router.routeToStartProcessScene(UserInfoActivity.IDENTIFY_USER) }
        }
    }
    //endregion


    //region UI - Add capture done observable
    private fun addCaptureDoneObservable(){
        disposable = subject.subscribe { isValid ->
            Log.i(TAG, "addCaptureDoneObservable: isValid -> $isValid")
            button_start_process.isEnabled = isValid
        }
    }
    //endregion

    //region Android - On Activity resut
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            Log.i(TAG, "Request code was $requestCode")
            when(resultCode){
                IdentifyModels.RequestCode.REQUEST_CODE_FACE.ordinal -> subject.onNext(true)
                IdentifyModels.RequestCode.REQUEST_CODE_HAND_LETT.ordinal,
                IdentifyModels.RequestCode.REQUEST_CODE_HAND_RIGHT.ordinal -> subject.onNext(true)
            }
        }else{
            Log.e(TAG, "Activity result: canceled")
            //TODO: Delete line below
            subject.onNext(true)
        }
    }
    //endregion
}

/**
 *  Identify Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyDisplayLogic {
    fun displayGoToNextScene(viewModel: IdentifyModels.GoToNextScene.ViewModel)
}