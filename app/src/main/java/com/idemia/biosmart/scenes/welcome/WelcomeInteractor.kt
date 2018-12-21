package com.idemia.biosmart.scenes.welcome

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  Welcome Interactor
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeInteractor : WelcomeBusinessLogic {
    private val worker = WelcomeWorker()
    private var presenter: WelcomePresentationLogic = WelcomePresenter()
    private var disposable: Disposable? = null

    companion object {
        val TAG = "WelcomeInteractor"
    }

    fun setPresenter(presenter: WelcomePresentationLogic) {
        this.presenter = presenter
    }

    override fun generateLicense(request: WelcomeModels.GenerateLicense.Request) {
        // Call WS to generate license file bin (Service Provider)
        disposable = worker.generateLicense().subscribe({ response ->
            val activationData = response.bytes()
            val mResponse = WelcomeModels.GenerateLicense.Response(true, activationData)
            presenter.presentGenerateLicense(mResponse)
            disposable!!.dispose()
        },{ throwable ->
            val response = WelcomeModels.GenerateLicense.Response(false)
            Log.e(TAG,"Error generating BIN File License due: ", throwable)
            presenter.presentGenerateLicense(response)
            disposable!!.dispose()
        })
    }

    override fun createLKMSLicense(request: WelcomeModels.ActivateBinFileLicenseToLkms.Request) {
        disposable = worker.createLKMSLicense(request)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ lkmsLicense ->
                val response = WelcomeModels.ActivateBinFileLicenseToLkms.Response(true, lkmsLicense)
                presenter.presentCreateLKMSLicense(response)
                disposable!!.dispose()
            },{ throwable ->
                Log.e(TAG, "License not activated due: ", throwable)
                val response = WelcomeModels.ActivateBinFileLicenseToLkms.Response(false)
                presenter.presentCreateLKMSLicense(response)
                disposable!!.dispose()
            })
    }

    override fun activateLkmsLicenseOnDevice(request: WelcomeModels.ActivateLkmsLicenseOnDevice.Request) {
        val response = worker.activateLkmsLicenseOnDevice(request)
        presenter.presentActivateLkmsLicenseOnDevice(response)
    }

    override fun startProcess(request: WelcomeModels.StartEnrollment.Request) {
        val response = WelcomeModels.StartEnrollment.Response(request.operation)
        presenter.presentStartProcess(response)
    }
}


/**
 *  Welcome Business Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface WelcomeBusinessLogic {
    /**
     * Generate License from Service Provider
     * @param requuest A GenerateLicense Request to send
     * */
    fun generateLicense(request: WelcomeModels.GenerateLicense.Request)

    /**
     * Create LKMS License
     * @param  request A [WelcomeModels.ActivateBinFileLicenseToLkms.Request] request
     */
    fun createLKMSLicense(request: WelcomeModels.ActivateBinFileLicenseToLkms.Request)

    fun activateLkmsLicenseOnDevice(request: WelcomeModels.ActivateLkmsLicenseOnDevice.Request)

    /**
     * Start next process
     */
    fun startProcess(request: WelcomeModels.StartEnrollment.Request)
}
