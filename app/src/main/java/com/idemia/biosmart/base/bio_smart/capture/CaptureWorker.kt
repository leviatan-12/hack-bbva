package com.idemia.biosmart.base.bio_smart.capture;

import android.util.Log
import com.morpho.mph_bio_sdk.android.sdk.BioSdk
import com.morpho.mph_bio_sdk.android.sdk.common.LogLevel
import com.morpho.mph_bio_sdk.android.sdk.morpholite.BioMatcherSettings
import com.morpho.mph_bio_sdk.android.sdk.morpholite.IBioMatcherHandler
import com.morpho.mph_bio_sdk.android.sdk.morpholite.async.BioMatcherAsyncCallbacks
import com.morpho.mph_bio_sdk.android.sdk.msc.IBioCaptureHandler
import com.morpho.mph_bio_sdk.android.sdk.msc.async.MscAsyncCallbacks
import com.morpho.mph_bio_sdk.android.sdk.msc.data.BioCaptureMode
import com.morpho.mph_bio_sdk.android.sdk.msc.error.BioCaptureHandlerError
import io.reactivex.Single
import java.lang.Error
import java.lang.Exception

/**
 *  Capture Worker
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class CaptureWorker {
    companion object {
        const val TAG = "CaptureWorker"
    }

    // Read Preferences
    fun readPreferences(request: CaptureModels.ReadPreferences.Request): List<Any> {
        val preferenceManager = request.activity.preferenceManager
        val camera = preferenceManager.getBoolean("IDEMIA_KEY_USE_CAMERA_REAR", true)
        val torch = preferenceManager.getBoolean("IDEMIA_KEY_USE_TORCH", true)
        val overlay = preferenceManager.getBoolean("IDEMIA_KEY_USE_OVERLAY", true)
        val timeout = preferenceManager.getString("IDEMIA_KEY_CAPTURE_TIMEOUT", "10")
        val faceCaptureMode = preferenceManager.getString("IDEMIA_KEY_FACE_CAPTURE_MODE","")
        val fingersCaptureMode = preferenceManager.getString("IDEMIA_KEY_FINGERS_CAPTURE_MODE","")
        val challengeInterDelay = preferenceManager.getString("IDEMIA_KEY_CHALLENGE_INTER_DELAY", "3000")
        var captureMode: BioCaptureMode? = null
        when(request.handlerType){
            CaptureModels.CaptureHanlderType.FACIAL -> {
                captureMode = selectFaceCaptureMode(faceCaptureMode!!)
            }
            CaptureModels.CaptureHanlderType.FINGERS -> {
                captureMode = selectFingersCaptureMode(fingersCaptureMode!!)
            }
        }
        return listOf(camera, torch, overlay, timeout!!.toLong() , captureMode, challengeInterDelay)
    }

    fun readTimeBeforeStartCapture(request: CaptureModels.ReadPreferences.Request): Int {
        val preferenceManager = request.activity.preferenceManager
        val timeBeforeStartCapture = preferenceManager.getString("IDEMIA_KEY_TIME_BEFORE_START_CAPTURE","5")
        return timeBeforeStartCapture!!.toInt()
    }

    private fun selectFaceCaptureMode(mode: String): BioCaptureMode {
        when(mode){
            "TRACK_FACE_CHALLENGE_VERY_LOW" -> return BioCaptureMode.TRACK_FACE_CHALLENGE_VERY_LOW
            "TRACK_FACE_CHALLENGE_LOW" -> return BioCaptureMode.TRACK_FACE_CHALLENGE_LOW
            "TRACK_FACE_CHALLENGE_MEDIUM" -> return BioCaptureMode.TRACK_FACE_CHALLENGE_MEDIUM
            "TRACK_FACE_CHALLENGE_HIGH" -> return BioCaptureMode.TRACK_FACE_CHALLENGE_HIGH
            "TRACK_FACE_CHALLENGE_VERY_HIGH" -> return BioCaptureMode.TRACK_FACE_CHALLENGE_VERY_HIGH
            "TRACK_FACE_LIVENESS_LOW" -> return BioCaptureMode.TRACK_FACE_LIVENESS_LOW
            "TRACK_FACE_LIVENESS_MEDIUM" -> return BioCaptureMode.TRACK_FACE_LIVENESS_MEDIUM
            "TRACK_FACE_LIVENESS_HIGH" -> return BioCaptureMode.TRACK_FACE_LIVENESS_HIGH
            else -> return BioCaptureMode.TRACK_FACE_DEFAULT
        }
    }

    private fun selectFingersCaptureMode(mode: String): BioCaptureMode {
        when(mode){
            "FINGERPRINT_RIGHT_HAND" -> return BioCaptureMode.FINGERPRINT_RIGHT_HAND
            "FINGERPRINT_LEFT_HAND" -> return BioCaptureMode.FINGERPRINT_LEFT_HAND
            "FINGERPRINT_LEFT_HAND_AUTHENTICATION" -> return BioCaptureMode.FINGERPRINT_LEFT_HAND_AUTHENTICATION
            "FINGERPRINT_RIGHT_HAND_AUTHENTICATION" -> return BioCaptureMode.FINGERPRINT_RIGHT_HAND_AUTHENTICATION
            else -> return BioCaptureMode.FINGERPRINT_LEFT_HAND
        }
    }

    // Create Bio Capture Handler
    fun createBioCaptureHandler(request: CaptureModels.CreateCaptureHandler.Request): Single<IBioCaptureHandler> {
        return Single.create<IBioCaptureHandler>{ emitter ->
            BioSdk.createBioCaptureHandler(request.activity, request.captureOptions, object :
                MscAsyncCallbacks<IBioCaptureHandler> {
                override fun onPreExecute() { }

                override fun onSuccess(iBioCaptureHandler: IBioCaptureHandler) {
                    emitter.onSuccess(iBioCaptureHandler)
                }

                override fun onError(bioCaptureHandlerError: BioCaptureHandlerError) {
                    Log.e(TAG, "createBioCaptureHandler:onError() - ${bioCaptureHandlerError}" )
                    emitter.onError(Throwable("Error creating capture handler " +
                            "(${bioCaptureHandlerError.name}, code error: ${bioCaptureHandlerError.mscValue})"))
                }
            })
        }
    }

    fun createMatcherHandler(request: CaptureModels.CreateMatcherHandler.Request): Single<IBioMatcherHandler>{
        return Single.create<IBioMatcherHandler>{ emitter ->
            val matcherSettings = BioMatcherSettings()
            matcherSettings.logLevel = LogLevel.DEBUG
            matcherSettings.dumpFileFolder = null
            matcherSettings.isDumpFileEnable = false
            BioSdk.createBioMatcherHandler(request.activity, matcherSettings,
                object: BioMatcherAsyncCallbacks<IBioMatcherHandler> {

                    override fun onSuccess(p0: IBioMatcherHandler?) {
                        p0?.let {
                            emitter.onSuccess(p0)
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