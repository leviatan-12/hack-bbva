package com.idemia.biosmart.scenes.license

import android.util.Log

/**
 *  License Presenter
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class LicensePresenter : LicensePresentationLogic {
    private var activity: LicenseDisplayLogic? = null

    companion object {
        private const val TAG = "LicensePresenter"
    }

    fun setActivity(activity: LicenseDisplayLogic) {
        this.activity = activity
    }

    //region License Details
    override fun presentLicenseDetails(response: LicenseModels.LicenseDetails.Response) {
        Log.i(TAG, "presentDoSomething: ${response.license.id}")
        val licenseId = response.license.id
        val features = response.license.features
        val profileId = response.license.profileId
        val status = response.license.status
        val viewModel = LicenseModels.LicenseDetails.ViewModel(licenseId, features, profileId, status)
        activity!!.displayLicenseDetails(viewModel)
    }
    //endregion
}

/**
 *  License Presentation Logic
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface LicensePresentationLogic {
    fun presentLicenseDetails(response: LicenseModels.LicenseDetails.Response)
}