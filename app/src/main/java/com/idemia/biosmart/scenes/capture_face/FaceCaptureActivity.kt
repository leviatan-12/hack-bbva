package com.idemia.biosmart.scenes.capture_face

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.face.FaceCaptureActivity
import com.idemia.biosmart.utils.AppCache
import kotlinx.android.synthetic.main.activity_capture_face.*

class FaceCaptureActivity : FaceCaptureActivity() {
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = false
    override fun resourceLayoutId(): Int = R.layout.activity_capture_face
    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    private var countDownTimer: CountDownTimer? = null

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        super.onLoadActivity(savedInstanceState)
        initUi()
    }

    override fun readyForCapture() {
        startCountdown()
    }

    override fun onPause() {
        super.onPause()
        stopCountdown()
    }

    override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel) {
        tv_feedback_info.text = viewModel.message
    }

    override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel) {
        Log.i(TAG, "displayCaptureFinish()")
        showToast(getString(R.string.label_capture_finished))
        button_finish.visibility = View.VISIBLE
    }

    override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel) {
        // Retrieve face image
        viewModel.morphoImages?.let { list ->
            if(list.isNotEmpty()){ AppCache.facePhoto = list[0] }
        } ?: run {
            showToast(getString(R.string.fatal_morpho_face_image_null))
        }
        stopCapture()
        face_id_mask.visibility = View.INVISIBLE
    }

    override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel) {
        showToast("Capture failed due ${viewModel.captureError?.name}")
    }

    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        showToast("Error due: ${viewModel.throwable.localizedMessage}")
        button_finish.visibility = View.VISIBLE
    }

    private fun startCountdown(){
        tv_countdown.visibility = View.VISIBLE
        val startAt = (timeBeforeStartCapture * 1000).toLong()
        countDownTimer = createCountdownTimer(startAt ,1000, { tick ->
            runOnUiThread { tv_countdown.text = "${tick}s" }
        } ,{
            tv_countdown.visibility = View.GONE
            face_id_mask.visibility = View.VISIBLE
            startCapture()
        })
        countDownTimer?.start()
    }

    private fun stopCountdown(){
        tv_countdown.visibility = View.GONE
        countDownTimer?.cancel()
    }

    private fun initUi(){
        tv_feedback_info.text = getString(R.string.label_face_capture)
        face_id_mask.visibility = View.VISIBLE
        tv_countdown.visibility = View.GONE
        button_finish.visibility = View.GONE
        button_finish.setOnClickListener {
            finish()
        }
    }
}
