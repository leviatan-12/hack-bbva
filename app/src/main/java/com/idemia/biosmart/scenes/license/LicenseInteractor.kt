package com.idemia.biosmart.scenes.license

import android.util.Log

/**
 *  License Interactor
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class LicenseInteractor : LicenseBusinessLogic {
    private val worker = LicenseWorker()
    private var presenter: LicensePresentationLogic = LicensePresenter()

    companion object {
        private const val TAG = "LicenseInteractor"
    }

    fun setPresenter(presenter: LicensePresentationLogic) {
        this.presenter = presenter
    }

    //region License Details
    override fun licenseDetails(request: LicenseModels.LicenseDetails.Request) {
        Log.i(TAG, "Executing licenseDetails() method")
        val response = LicenseModels.LicenseDetails.Response(request.license)
        presenter.presentLicenseDetails(response)
    }
    //endregion
}


/**
 *  License Business Logic
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface LicenseBusinessLogic {
    fun licenseDetails(request: LicenseModels.LicenseDetails.Request)
}