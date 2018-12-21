package com.idemia.biosmart.scenes.welcome

import android.content.Intent
import com.idemia.biosmart.scenes.authenticate.AuthenticateActivity
import com.idemia.biosmart.scenes.enrolment.EnrolmentActivity
import com.idemia.biosmart.scenes.identify.IdentifyActivity
import com.idemia.biosmart.scenes.settings.SettingsActivity

import java.lang.ref.WeakReference

/**
 *  Welcome Router
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeRouter : WelcomeRoutingLogic {

    private var activity: WeakReference<WelcomeActivity>? = null

    fun setActivity(activity: WelcomeActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToEnrolmentScene() {
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, EnrolmentActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToAuthenticationScene() {
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, AuthenticateActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToIdentifyScene() {
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, IdentifyActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToSettingsScene(){
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, SettingsActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  Welcome Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface WelcomeRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToEnrolmentScene()
    fun routeToAuthenticationScene()
    fun routeToIdentifyScene()
    fun routeToSettingsScene()
}