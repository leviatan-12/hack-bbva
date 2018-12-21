package com.idemia.biosmart.scenes.welcome

/**
 *  Welcome Presenter
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomePresenter : WelcomePresentationLogic {
    private var activity: WelcomeDisplayLogic? = null

    companion object {
        private val TAG = "WelcomePresenter"
    }

    fun setActivity(activity: WelcomeDisplayLogic) {
        this.activity = activity
    }

    override fun presentGenerateLicense(response: WelcomeModels.GenerateLicense.Response) {
        val viewModel = WelcomeModels.GenerateLicense.ViewModel()
        viewModel.generated = response.generated
        viewModel.activationData = response.activationData
        activity!!.displayGenerateLicense(viewModel)
    }

    override fun presentCreateLKMSLicense(response: WelcomeModels.ActivateBinFileLicenseToLkms.Response) {
        val viewModel = WelcomeModels.ActivateBinFileLicenseToLkms.ViewModel(response.activated)
        activity!!.displayCreateLKMSLicense(viewModel)
    }

    override fun presentActivateLkmsLicenseOnDevice(response: WelcomeModels.ActivateLkmsLicenseOnDevice.Response) {
        val viewModel = WelcomeModels.ActivateLkmsLicenseOnDevice.ViewModel(response.isLicenseValid, response.lkmsLicense)
        activity!!.displayActivateLkmsLicenseOnDevice(viewModel)
    }

    override fun presentStartProcess(response: WelcomeModels.StartEnrollment.Response) {
        val viewModel = WelcomeModels.StartEnrollment.ViewModel(response.operation)
        activity!!.displayStartProcess(viewModel)
    }
}


/**
 *  Welcome Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface WelcomePresentationLogic {
    fun presentGenerateLicense(response: WelcomeModels.GenerateLicense.Response)
    fun presentCreateLKMSLicense(response: WelcomeModels.ActivateBinFileLicenseToLkms.Response)
    fun presentActivateLkmsLicenseOnDevice(response: WelcomeModels.ActivateLkmsLicenseOnDevice.Response)
    fun presentStartProcess(response: WelcomeModels.StartEnrollment.Response)
}