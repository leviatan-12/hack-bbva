package com.idemia.biosmart.scenes.enrolment

import java.lang.ref.WeakReference

/**
 *  Enrolment Router
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentRouter : EnrolmentRoutingLogic {

    private var activity: WeakReference<EnrolmentActivity>? = null

    fun setActivity(activity: EnrolmentActivity) {
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
 *  Enrolment Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}