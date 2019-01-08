package com.idemia.biosmart.scenes.enrolment

import android.content.Intent
import com.idemia.biosmart.scenes.capture_face.FaceCaptureActivity
import com.idemia.biosmart.scenes.capture_fingers.FingersCaptureActivity
import com.idemia.biosmart.scenes.enrolment_details.EnrolmentDetailsActivity
import com.idemia.biosmart.scenes.face_info.FaceInfoActivity
import java.lang.ref.WeakReference

/**
 *  Enrolment Router
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentRouter : EnrolmentRoutingLogic {

    private var activity: WeakReference<EnrolmentActivity>? = null

    fun setActivity(activity: EnrolmentActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToCaptureFingersScene() {
        if (activity?.get() != null) {
            val intent = Intent(activity!!.get()!!.applicationContext, FingersCaptureActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, EnrolmentModels.RequestCode.REQUEST_CODE_HAND_LETT.ordinal)
        }
    }

    override fun routeToCaptureFingersMsoScene() {

    }

    override fun routeToCaptureFaceScene() {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, FaceInfoActivity::class.java)
            activity!!.get()!!.startActivityForResult(intent, EnrolmentModels.RequestCode.REQUEST_CODE_FACE.ordinal)
        }
    }

    override fun routeToStartProcessScene() {
        if (activity?.get() != null){
            val intent = Intent(activity!!.get()!!.applicationContext, EnrolmentDetailsActivity::class.java)
            activity!!.get()!!.startActivity(intent)
        }
    }
}

/**
 *  Enrolment Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentRoutingLogic {
    /**
     * Route to Capture Fingers Scene
     */
    fun routeToCaptureFingersScene()

    fun routeToCaptureFingersMsoScene()

    fun routeToCaptureFaceScene()

    fun routeToStartProcessScene()
}