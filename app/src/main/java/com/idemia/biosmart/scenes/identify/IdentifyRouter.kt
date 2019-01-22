package com.idemia.biosmart.scenes.identify

import android.content.Intent
import com.idemia.biosmart.scenes.capture_fingers.FingersCaptureActivity
import com.idemia.biosmart.scenes.face_info.FaceInfoActivity
import com.idemia.biosmart.scenes.fingers_info.FingersInfoActivity
import com.idemia.biosmart.scenes.user_info.UserInfoActivity
import com.idemia.biosmart.utils.AppCache
import java.lang.ref.WeakReference
import java.util.*

/**
 *  Identify Router
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyRouter : IdentifyRoutingLogic {

    private var activity: WeakReference<IdentifyActivity>? = null

    fun setActivity(activity: IdentifyActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToCaptureFingersScene() {
        if (activity?.get() != null) {
            val intent = Intent(activity!!.get()!!.applicationContext, FingersInfoActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, IdentifyModels.RequestCode.REQUEST_CODE_HAND_LEFT.ordinal)
        }
    }

    override fun routeToCaptureFingersMsoScene() {

    }

    override fun routeToCaptureFaceScene() {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, FaceInfoActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, IdentifyModels.RequestCode.REQUEST_CODE_FACE.ordinal)
        }
    }

    override fun routeToStartProcessScene(operation: Int) {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, UserInfoActivity::class.java)
            AppCache.username = UUID.randomUUID().toString() // RANDOM USER ID
            intent.putExtra(UserInfoActivity.KEY_OPERATION_TYPE, operation)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  Identify Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyRoutingLogic {
    fun routeToCaptureFingersScene()

    fun routeToCaptureFingersMsoScene()

    fun routeToCaptureFaceScene()

    fun routeToStartProcessScene(operation: Int)
}