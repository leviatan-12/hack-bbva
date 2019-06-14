package com.idemia.biosmart.scenes.face_info;

import android.content.Intent;
import com.idemia.biosmart.scenes.capture_face.FaceCaptureActivity

import java.lang.ref.WeakReference;

/**
 *  FaceInfo Router
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FaceInfoRouter : FaceInfoRoutingLogic {

    private var activity: WeakReference<FaceInfoActivity>? = null

    fun setActivity(activity: FaceInfoActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToNextScene() {
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, FaceCaptureActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  FaceInfo Routing Logic
 *  BioSmart
 *  Created by alfredo on 1/4/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FaceInfoRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene()
}