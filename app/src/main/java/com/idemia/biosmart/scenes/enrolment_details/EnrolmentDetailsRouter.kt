package com.idemia.biosmart.scenes.enrolment_details;

import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 *  EnrolmentDetails Router
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsRouter : EnrolmentDetailsRoutingLogic {

    private var activity: WeakReference<EnrolmentDetailsActivity>? = null

    fun setActivity(activity: EnrolmentDetailsActivity) {
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
 *  EnrolmentDetails Routing Logic
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface EnrolmentDetailsRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}