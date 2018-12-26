package com.idemia.biosmart.scenes.identify

import java.lang.ref.WeakReference

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
 *  Identify Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface IdentifyRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}