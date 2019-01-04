package com.idemia.biosmart.base.bio_smart.capture;

import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 *  Capture Router
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureRouter : CaptureRoutingLogic {

    private var activity: WeakReference<CaptureActivity>? = null

    fun setActivity(activity: CaptureActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToNextScene(name: String) {
        if (activity?.get() != null) {
            /*Intent intent = new Intent(activity.get().getApplicationContext(), NextSceneActivity.class);
            intent.putExtra("user_name", name);
            activity.get().startActivity(intent);
            */
        }
    }
}

/**
 *  Capture Routing Logic
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface CaptureRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}