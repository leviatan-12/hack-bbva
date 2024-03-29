package com.idemia.biosmart.scenes.welcome

import javax.inject.Inject

/**
 *  Welcome Presenter
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomePresenter @Inject constructor(val activity: WelcomeDisplayLogic) : WelcomePresentationLogic {

    companion object {
        private val TAG = "WelcomePresenter"
    }

    override fun presentGenerateLicense(response: WelcomeModels.GenerateLicense.Response) {
        val viewModel = WelcomeModels.GenerateLicense.ViewModel()
        viewModel.apply {
            generated = response.generated
            activationData = response.activationData
            response.throwable?.message?.let {
                message = it
            }
        }
        activity.displayGenerateLicense(viewModel)
    }

    override fun presentCreateLKMSLicense(response: WelcomeModels.ActivateBinFileLicenseToLkms.Response) {
        val viewModel = WelcomeModels.ActivateBinFileLicenseToLkms.ViewModel(response.activated, response.lkmsLicense, response.throwable)
        activity.displayCreateLKMSLicense(viewModel)
    }

    override fun presentActivateLkmsLicenseOnDevice(response: WelcomeModels.ActivateLkmsLicenseOnDevice.Response) {
        val viewModel = WelcomeModels.ActivateLkmsLicenseOnDevice.ViewModel(response.isLicenseValid, response.lkmsLicense)
        activity.displayActivateLkmsLicenseOnDevice(viewModel)
    }

    override fun presentStartProcess(response: WelcomeModels.StartEnrollment.Response) {
        val viewModel = WelcomeModels.StartEnrollment.ViewModel(response.operation)
        activity.displayStartProcess(viewModel)
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