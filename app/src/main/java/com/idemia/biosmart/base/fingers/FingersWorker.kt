package com.idemia.biosmart.base.fingers

import com.morpho.mph_bio_sdk.android.sdk.BioSdk
import com.morpho.mph_bio_sdk.android.sdk.morpholite.BioMatcherSettings
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBioMatcherHandler
import com.morpho.mph_bio_sdk.android.sdk.morpholite.async.BioMatcherAsyncCallbacks
import com.morpho.mph_bio_sdk.android.sdk.msc.IBioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.async.MscAsyncCallbacks
import com.morpho.mph_bio_sdk.android.sdk.msc.error.BioCaptureHandlerError
import io.reactivex.Observable
import java.lang.Error
import java.lang.Exception

/**
 *  Fingers Worker
 *  biosmartapp-android
 *  Created by Alfredo on 28/12/2018.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class FingersWorker {
    // Create Bio Capture Handler
    fun createBioCaptureHandler(request: FingersModels.CreateCaptureHandler.Request): Observable<IBioCaptureHandler>{
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


    fun createMatcherHandler(request: FingersModels.CreateMatcherHandler.Request): Observable<IBioMatcherHandler>{
        return Observable.create<IBioMatcherHandler>{ emitter ->
            BioSdk.createBioMatcherHandler(request.activity, BioMatcherSettings(), object: BioMatcherAsyncCallbacks<IBioMatcherHandler>{
                override fun onSuccess(p0: IBioMatcherHandler?) {
                    p0?.let {
                        emitter.onNext(it)
                        emitter.onComplete()
                    }?: run {
                        emitter.onError(Error("Matcher Handler was null on creation"))
                    }
                }

                override fun onError(p0: Exception?) {
                    emitter.onError(Throwable(p0))
                }

                override fun onPreExecute() {}
            })
        }
    }
}