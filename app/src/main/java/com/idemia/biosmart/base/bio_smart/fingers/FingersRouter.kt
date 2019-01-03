package com.idemia.biosmart.base.bio_smart.fingers

import java.lang.ref.WeakReference

/**
 *  Fingers Router
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersRouter : FingersRoutingLogic {

    private var activity: WeakReference<FingersActivity>? = null

    fun setActivity(activity: FingersActivity) {
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
 *  Fingers Routing Logic
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface FingersRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}