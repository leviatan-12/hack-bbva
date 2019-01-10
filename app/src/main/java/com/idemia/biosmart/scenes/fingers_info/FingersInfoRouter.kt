package com.idemia.biosmart.scenes.fingers_info;

import android.content.Intent;
import com.idemia.biosmart.scenes.capture_fingers.FingersCaptureActivity

import java.lang.ref.WeakReference;

/**
 *  FingersInfo Router
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoRouter : FingersInfoRoutingLogic {

    private var activity: WeakReference<FingersInfoActivity>? = null

    fun setActivity(activity: FingersInfoActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToNextScene() {
        if (activity?.get() != null) {
            val intent = Intent(activity!!.get()!!.applicationContext, FingersCaptureActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  FingersInfo Routing Logic
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface FingersInfoRoutingLogic {
    fun routeToNextScene()
}