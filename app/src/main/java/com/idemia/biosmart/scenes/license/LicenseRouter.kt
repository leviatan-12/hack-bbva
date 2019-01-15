package com.idemia.biosmart.scenes.license

import android.content.Intent
import android.util.Log

import java.lang.ref.WeakReference

/**
 *  License Router
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class LicenseRouter : LicenseRoutingLogic {
    private var activity: WeakReference<LicenseActivity>? = null

    companion object {
        private const val TAG = "LicenseRouter"
    }

    fun setActivity(activity: LicenseActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToNextScene(name: String) {
        Log.i(TAG, "Routing to Next Scene")
        if (activity?.get() != null) {
            /*Intent intent = new Intent(activity.get().getApplicationContext(), NextSceneActivity.class);
            intent.putExtra("user_name", name);
            activity.get().startActivity(intent);
            */
        }
    }
}

/**
 *  License Routing Logic
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface LicenseRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}