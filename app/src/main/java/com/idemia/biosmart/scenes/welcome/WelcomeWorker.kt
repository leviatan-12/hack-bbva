package com.idemia.biosmart.scenes.welcome

import android.util.Log
import com.idemia.biosmart.api.SDKApiService
import com.morpho.lkms.android.sdk.lkms_core.exceptions.LkmsException
import com.morpho.lkms.android.sdk.lkms_core.exceptions.LkmsNoLicenseAvailableException
import com.morpho.lkms.android.sdk.lkms_core.license.ILkmsLicense
import com.morpho.lkms.android.sdk.lkms_core.network.NetworkSettings
import com.morpho.mph_bio_sdk.android.sdk.BioSdk
import com.morpho.mph_bio_sdk.android.sdk.licence.async.BioSdkLicenceAsyncCallbacks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 *  Welcome Worker
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeWorker {
    companion object {
        val TAG = "WelcomeWorker"
    }
    val apiService by lazy { SDKApiService.create("http://200.66.76.220:8081/ServiceProviderBPD/") }

    /**
     * Make your WS calls here
     */
    fun generateLicense(): Observable<ResponseBody>{
        return apiService.generateLicenseBinFile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /** Create LKMS License */
    fun createLKMSLicense(request: WelcomeModels.ActivateBinFileLicenseToLkms.Request): Observable<ILkmsLicense>{
        val licenseManager = BioSdk.createLicenseManager(request.applicationContext)
        return Observable.create<ILkmsLicense> { emitter ->
            licenseManager.createLicense(request.applicationContext, request.lkmsUrl, networkSettings(), request.activationData,
                object : BioSdkLicenceAsyncCallbacks<ILkmsLicense> {
                    override fun onPreExecute() {
                        Log.i(TAG,"onPreExecute")
                    }

                    override fun onSuccess(result: ILkmsLicense) {
                        Log.i(TAG,"onSuccess, ID: ${result.id}")
                        emitter.onNext(result)
                        emitter.onComplete()
                    }

                    override fun onError(e: LkmsException) {
                        Log.i(TAG,"onError", e)
                        emitter.onError(e)
                    }
                })
        }
    }

    fun activateLkmsLicenseOnDevice(request: WelcomeModels.ActivateLkmsLicenseOnDevice.Request): WelcomeModels.ActivateLkmsLicenseOnDevice.Response{
        val licenseManager = BioSdk.createLicenseManager(request.applicationContext)
        try{
            val license: ILkmsLicense = licenseManager.retrieveLicense(request.applicationContext)
            licenseManager.activate(request.applicationContext)
            return WelcomeModels.ActivateLkmsLicenseOnDevice.Response(true, license)
        }catch (e: LkmsNoLicenseAvailableException){
            // License is not valid
            Log.e(TAG, "activateLkmsLicenseOnDevice", e)
        }
        return WelcomeModels.ActivateLkmsLicenseOnDevice.Response(false)
    }

    private fun networkSettings(): NetworkSettings{
        val networkSettings = NetworkSettings()
        networkSettings.timeoutInSeconds = 60
        networkSettings.sslSocketFactory = null
        networkSettings.x509TrustManager = null
        networkSettings.hostNameVerifier = null
        return networkSettings
    }
}