package com.idemia.biosmart.scenes.authenticate

import android.content.Intent
import com.idemia.biosmart.scenes.capture_face.CaptureFaceActivity
import com.idemia.biosmart.scenes.capture_fingers.CaptureFingersActivity
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
            val intent = Intent(activity!!.get()!!.applicationContext, CaptureFingersActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToCaptureFingersMsoScene() {

    }

    override fun routeToCaptureFaceScene() {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, CaptureFaceActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToStartProcessScene() {

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

    fun routeToStartProcessScene()
}