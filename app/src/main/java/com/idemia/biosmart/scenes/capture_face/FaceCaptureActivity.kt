package com.idemia.biosmart.scenes.capture_face

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.face.FaceCaptureActivity
import com.idemia.biosmart.base.utils.DisposableManager
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
        addListeners()
    }

    //region CAPTURE - Ready for capture
    override fun readyForCapture() {
        startCountdown()
    }
    //endregion

    //region CAPURE - Use torch
    override fun displayUseTorch(viewModel: CaptureModels.UseTorch.ViewModel) {
        displayTorchEnabled(viewModel.isTorchOn)
    }
    //endregion

    //region ANDROD - OnPause
    override fun onPause() {
        super.onPause()
        stopCountdown()
    }
    //endregion

    //region USE CASE - Capture Info
    override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel){
        if(viewModel.hasMessage)
            tv_feedback_info.text = viewModel.message
        else
            tv_feedback_info.text = getString(R.string.face_capture_please_wait_for_next_challenge)
    }
    //endregion

    //region USE CASE - Capture finish
    override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel) {
        Log.i(TAG, "displayCaptureFinish()")
        // showToast(getString(R.string.label_capture_finished))
    }
    //endregion

    //region USE CASE - Capture success
    override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel) {
        // Retrieve face image
        viewModel.morphoImages?.let { list ->
            if(list.isNotEmpty()){
                AppCache.facePhoto = list[0]
                showImageCaptured()
            }
        } ?: run {
            showToast(getString(R.string.fatal_morpho_face_image_null))
        }
        successUi()
    }
    //endregion

    //region USECASE - Capture failed
    override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel) {
        if(viewModel.hasMessage) {
            showToast("Capture Failed due: ${viewModel.message}")
            tv_feedback_info.text = getString(R.string.label_capture_failed_due, viewModel.message)
        } else{
            showToast("Capture failed due: ${viewModel.captureError?.name}")
            tv_feedback_info.text = getString(R.string.label_capture_failed_due, viewModel.captureError?.name)
        }
        errorUi()
    }
    //endregion

    //region USE CASE - Error
    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        showToast("Error due: ${viewModel.throwable.localizedMessage}")
        errorUi()
    }
    //endregion

    //region USE CASE - Start Countdown
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
    //endregion

    //region USE CASE - Stop countdown
    private fun stopCountdown(){
        tv_countdown.visibility = View.GONE
        countDownTimer?.cancel()
    }
    //endregion

    //region USE CASE - Show image captured
    private fun showImageCaptured(){
        // TODO: Create a use case "retrieve selfie"
        AppCache.facePhoto?.let { photo ->
            val data = photo.jpegImage
            val bmp = BitmapFactory.decodeByteArray(data, 0, data!!.size)
            iv_selfie.setImageBitmap(bmp)
        }
    }
    //endregion

    //region UI - Init UI
    private fun initUi(){
        addFadeInAnimationToFaceIdMask(3)
        switch_torch.visibility = View.VISIBLE
        tv_feedback_info.text = getString(R.string.label_face_capture)
        tv_feedback_info.visibility = View.VISIBLE
        face_id_mask.visibility = View.VISIBLE
        face_id_mask.animate()
        tv_countdown.visibility = View.GONE
        button_finish.visibility = View.GONE
        button_restart.visibility = View.GONE
        iv_selfie.scaleType = ImageView.ScaleType.CENTER_CROP
        iv_selfie.visibility = View.GONE
    }
    //endregion

    //region UI - Add listeners
    private fun addListeners(){
        switch_torch.setOnCheckedChangeListener { _, _ ->
            useTorch()
        }
        button_finish.setOnClickListener {
            finish()
        }
        button_restart.setOnClickListener {
            initUi()
            startCountdown()
        }
    }
    //endregion

    //region UI - Success Ui
    private fun successUi(){
        runOnUiThread {
            iv_selfie.visibility = View.VISIBLE
            switch_torch.visibility = View.GONE
            tv_feedback_info.visibility = View.VISIBLE
            tv_feedback_info.text = getString(R.string.face_capture_face_capture_scan_id_completed)
            face_id_mask.visibility = View.INVISIBLE
            button_restart.show()
            button_finish.show()
        }
    }
    //endregion

    //region UI - Error Ui
    private fun errorUi(){
       runOnUiThread {
           switch_torch.visibility = View.GONE
           // face_id_mask.visibility = View.VISIBLE
           addFadeOutAnimationToFaceIdMask(3)
           face_id_mask.animate()
           tv_feedback_info.visibility = View.VISIBLE
           iv_selfie.scaleType = ImageView.ScaleType.CENTER_INSIDE
           iv_selfie.setImageDrawable(getDrawable(R.drawable.ic_failed))
           iv_selfie.visibility = View.VISIBLE
           button_restart.show()
           button_finish.show()
       }
    }
    //endregion

    //region UI - Add Fade In animation to face ID mask
    private fun addFadeInAnimationToFaceIdMask(seconds: Int){
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = (seconds * 1000).toLong()
        face_id_mask.animation = fadeIn
    }
    //endregion

    //region UI - Add Fade Out animation to face ID mask
    private fun addFadeOutAnimationToFaceIdMask(seconds: Int){
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = DecelerateInterpolator()
        fadeOut.duration = (seconds * 1000).toLong()
        face_id_mask.animation = fadeOut
    }
    //endregion

    //region UI - Display torch enabled
    /**
     * If torch is on, should display a button with torch off image,
     * otherwise should display a torch on button
     * @param isTorchOn True if torch is on
     */
    private fun displayTorchEnabled(isTorchOn: Boolean){
        Log.i(TAG, "Is torch on: $isTorchOn")
    }
    //endregion
}
