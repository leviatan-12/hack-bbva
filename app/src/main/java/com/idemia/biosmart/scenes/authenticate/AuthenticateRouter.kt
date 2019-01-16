package com.idemia.biosmart.scenes.authenticate

import android.content.Intent
import com.idemia.biosmart.scenes.capture_fingers.FingersCaptureActivity
import com.idemia.biosmart.scenes.face_info.FaceInfoActivity
import com.idemia.biosmart.scenes.fingers_info.FingersInfoActivity
import com.idemia.biosmart.scenes.user_info.UserInfoActivity
import com.idemia.biosmart.utils.AppCache
import java.lang.ref.WeakReference

/**
 *  Authenticate Router
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticateRouter : AuthenticateRoutingLogic {

    private var activity: WeakReference<AuthenticateActivity>? = null

    fun setActivity(activity: AuthenticateActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToCaptureFingersScene() {
        if (activity?.get() != null) {
            val intent = Intent(activity!!.get()!!.applicationContext, FingersInfoActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, AuthenticateModels.RequestCode.REQUEST_CODE_HAND_LETT.ordinal)
        }
    }

    override fun routeToCaptureFingersMsoScene() {

    }

    override fun routeToCaptureFaceScene() {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, FaceInfoActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, AuthenticateModels.RequestCode.REQUEST_CODE_FACE.ordinal)
        }
    }

    override fun routeToStartProcessScene(operation: Int, username: String) {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, UserInfoActivity::class.java)
            AppCache.username = username
            intent.putExtra(UserInfoActivity.KEY_OPERATION_TYPE, operation)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  Authenticate Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface AuthenticateRoutingLogic {
    /**
     * Route to Capture Fingers Scene
     */
    fun routeToCaptureFingersScene()

    fun routeToCaptureFingersMsoScene()

    fun routeToCaptureFaceScene()

    fun routeToStartProcessScene(operation: Int, username: String)
}