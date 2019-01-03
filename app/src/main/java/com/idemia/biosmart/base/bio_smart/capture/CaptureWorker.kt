package com.idemia.biosmart.base.bio_smart.capture;

import com.morpho.mph_bio_sdk.android.sdk.BioSdk
import com.morpho.mph_bio_sdk.android.sdk.msc.IBioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.async.MscAsyncCallbacks
import com.morpho.mph_bio_sdk.android.sdk.msc.error.BioCaptureHandlerError
import io.reactivex.Observable

/**
 *  Capture Worker
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureWorker {
    // Read Preferences
    fun readPreferences(request: CaptureModels.ReadPreferences.Request): List<Any> {
        val preferenceManager = request.activity.preferenceManager
        val camera = preferenceManager.getBoolean("KEY_USE_CAMERA_REAR", true)
        val torch = preferenceManager.getBoolean("KEY_USE_TORCH", true)
        val overlay = preferenceManager.getBoolean("KEY_USE_OVERLAY", true)
        val timeout = preferenceManager.getLong("KEY_CAPTURE_TIMEOUT", 10)
        return listOf(camera, torch, overlay, timeout)
    }

    // Create Bio Capture Handler
    fun createBioCaptureHandler(request: CaptureModels.CreateCaptureHandler.Request): Observable<IBioCaptureHandler> {
        return Observable.create<IBioCaptureHandler>{ emitter ->
            BioSdk.createBioCaptureHandler(request.activity, request.captureOptions, object :
                MscAsyncCallbacks<IBioCaptureHandler> {
                override fun onPreExecute() {}

                override fun onSuccess(iBioCaptureHandler: IBioCaptureHandler) {
                    emitter.onNext(iBioCaptureHandler)
                    emitter.onComplete()
                }

                override fun onError(bioCaptureHandlerError: BioCaptureHandlerError) {
                    emitter.onError(Throwable("Error creating capture handler " +
                            "(${bioCaptureHandlerError.name}, code error: ${bioCaptureHandlerError.mscValue})"))
                }
            })
        }
    }

}