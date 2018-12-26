package com.idemia.biosmart.scenes.authenticate

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
 *  Authenticate Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface AuthenticateRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}