package com.idemia.biosmart.scenes.welcome

import android.content.Intent
import com.idemia.biosmart.scenes.authenticate.AuthenticateActivity
import com.idemia.biosmart.scenes.enrolment.EnrolmentActivity
import com.idemia.biosmart.scenes.identify.IdentifyActivity
import com.idemia.biosmart.scenes.license.LicenseActivity
import com.idemia.biosmart.scenes.settings.SettingsActivity
import com.idemia.biosmart.utils.AppCache
import com.morpho.lkms.android.sdk.lkms_core.license.ILkmsLicense

import java.lang.ref.WeakReference

/**
 *  Welcome Router
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeRouter : WelcomeRoutingLogic {

    var activity: WeakReference<WelcomeActivity>? = null

    override fun routeToEnrolmentScene() {
        if (activity?.get() != null) {
            AppCache.clearCache()
            val intent = Intent( activity!!.get()!!.applicationContext, EnrolmentActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToAuthenticationScene() {
        if (activity?.get() != null) {
            AppCache.clearCache()
            val intent = Intent( activity!!.get()!!.applicationContext, AuthenticateActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }

    override fun routeToIdentifyScene() {
        if (activity?.get() != null) {
            AppCache.clearCache()
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

    override fun routeToLicenseDetails() {
        if (activity?.get() != null) {
            val intent = Intent( activity!!.get()!!.applicationContext, LicenseActivity::class.java)
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
    fun routeToEnrolmentScene()
    fun routeToAuthenticationScene()
    fun routeToIdentifyScene()
    fun routeToSettingsScene()
    fun routeToLicenseDetails()
}