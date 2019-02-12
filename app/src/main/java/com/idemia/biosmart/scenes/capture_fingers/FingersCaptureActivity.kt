package com.idemia.biosmart.scenes.capture_fingers

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.fingers.FingersActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.utils.AppCache
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import kotlinx.android.synthetic.main.activity_capture_fingers.*

class FingersCaptureActivity : FingersActivity() {

    companion object { val TAG = "FingersCaptureActivity" }

    override fun resourceLayoutId(): Int = R.layout.activity_capture_fingers
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = false
    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    private var countDownTimer: CountDownTimer? = null

    private var leftHandTaken = false
    private var rightHandTaken = false
    private var captureLeftHand = false
    private var captureRightHand = false
    private var capturingLeftHand = true  // false for right hand capture

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        super.onLoadActivity(savedInstanceState)
        whichHandsToCapture()   // Load which hands to capture?
        initUi()                // init ui
    }

    //region ANDROID - OnPause
    override fun onPause() {
        super.onPause()
        stopCountdown()
    }
    //endregion

    //region CAPTURE - Ready for capture
    /** When SDK is ready for capture, this method will be executed */
    override fun readyForCapture() {
        Log.i(TAG, "readyForCapture(): BioSmart SDK is ready to start capture!")
        startCountdown()
    }
    //endregion

    //region CAPTURE - Use Torch
    override fun displayUseTorch(viewModel: CaptureModels.UseTorch.ViewModel) {
        Log.i(TAG, "displayUseTorch: Is torch on? ->${viewModel.isTorchOn}")
        displayTorchEnabled(viewModel.isTorchOn)
    }
    //endregion

    //region USECASE - Display Capture Info
    override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel) {
        Log.i(TAG, "displayCaptureInfo: ${viewModel.message}")
        text_view_feedback_info.text = viewModel.message
    }
    //endregion

    //region USECASE - Display Capture Finish
    override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel) {
        Log.i(TAG, "displayCaptureFinish()")
        Toast.makeText(applicationContext, getString(R.string.label_capture_finished) ,Toast.LENGTH_LONG).show()
        uiOnSuccess()
    }
    //endregion

    //region USECASE - Display Capture Success
    override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel) {
        Log.i(TAG, "displayCaptureSuccess()")
        viewModel.morphoImages?.let { imageList ->
            if(capturingLeftHand) {
                if(imageList.size == 4){
                    AppCache.imageListLeft = ArrayList()
                    (AppCache.imageListLeft as ArrayList).add(imageList[0])
                    (AppCache.imageListLeft as ArrayList).add(imageList[1])
                    (AppCache.imageListLeft as ArrayList).add(imageList[2])
                    (AppCache.imageListLeft as ArrayList).add(imageList[3])
                    leftHandTaken = true
                    startCountdown()    // FIX FOR NOW...
                }else{
                    AppCache.imageListLeft = null
                }
            }else if(!capturingLeftHand) {
                if(imageList.size == 4) {
                    AppCache.imageListRight = ArrayList()
                    (AppCache.imageListRight as ArrayList).add(imageList[0])
                    (AppCache.imageListRight as ArrayList).add(imageList[1])
                    (AppCache.imageListRight as ArrayList).add(imageList[2])
                    (AppCache.imageListRight as ArrayList).add(imageList[3])
                    rightHandTaken = true
                    startCountdown()    // FIX FOR NOW...
                }else{
                    AppCache.imageListRight = null
                }
            }else {
                Log.e(TAG, "No left/right hand to retrieve images")
            }
        }
    }
    //endregion

    //region USECASE - Display Capture Failure
    override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel) {
        Log.e(TAG, "${viewModel.captureError?.name} - Error code: ${viewModel.captureError?.ordinal}")
        viewModel.captureError?.let { error ->
            Toast.makeText(applicationContext, getString(R.string.label_error, error.name), Toast.LENGTH_LONG).show()
        }
    }
    //endregion

    //region USECASE - Display Error
    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        Log.e(TAG, "displayError: ${viewModel.throwable.localizedMessage}")
        Toast.makeText(applicationContext,
            viewModel.throwable.localizedMessage + " - Please verify your license status",
            Toast.LENGTH_LONG).show()
        uiOnError()
    }
    //endregion

    //region Which Hands to capture
    private fun whichHandsToCapture(){
        captureLeftHand = preferenceManager.getBoolean("IDEMIA_KEY_CAPTURE_LEFT_HAND", false)
        captureRightHand = preferenceManager.getBoolean("IDEMIA_KEY_CAPTURE_RIGHT_HAND", false)
        leftHandTaken = !captureLeftHand
        rightHandTaken = !captureRightHand
    }
    //endregion

    //region UI - Init Ui
    private fun initUi(){
        tv_countdown.visibility = View.GONE
        text_view_feedback_info.visibility = View.VISIBLE
        button_finish.hide()
        button_finish.setOnClickListener {
            finish()
        }
        switch_torch.setOnCheckedChangeListener { _ , _ ->
            useTorch()
        }
    }
    //endregion

    //region UI - Ui on success
    private fun uiOnSuccess(){
        text_view_feedback_info.visibility = View.GONE
        button_finish.show()
        setResult(Activity.RESULT_OK)
    }
    //endregion

    //region UI - Ui on error
    private fun uiOnError(){
        text_view_feedback_info.visibility = View.GONE
        button_finish.show()
        setResult(Activity.RESULT_CANCELED)
    }
    //endregion

    //region UI - Start Countdown
    private fun startCountdown(){
        Log.i(TAG, "startCountdown()")
        val startAt = (timeBeforeStartCapture * 1000).toLong()
        if(!leftHandTaken){
            Log.i(TAG, "startCountdown: Capture Left Hand")
            capturingLeftHand = true
            tv_countdown.visibility = View.VISIBLE
            countDownTimer = createCountdownTimer(startAt,1000, { tick ->
                runOnUiThread { tv_countdown.text = "${tick}s to capture left hand" }
            } ,{
                tv_countdown.visibility = View.GONE
                startCapture()
            })
            countDownTimer?.start()
        }else if(!rightHandTaken){
            Log.i(TAG, "startCountdown: Capture Right Hand")
            capturingLeftHand = false
            tv_countdown.visibility = View.VISIBLE
            countDownTimer = createCountdownTimer(startAt,1000, { tick ->
                runOnUiThread { tv_countdown.text = "${tick}s to capture right hand" }
            } ,{
                tv_countdown.visibility = View.GONE
                startCapture()
            })
            countDownTimer?.start()
        }else {
            Log.i(TAG, "No hands to capture")
        }
    }
    //endregion

    //region UI - Stop Countdown
    private fun stopCountdown(){
        Log.i(TAG, "stopCountdown()")
        tv_countdown.visibility = View.GONE
        countDownTimer?.cancel()
    }
    //endregion

    //region UI - Display torch enabled
    /**
     * If torch is on, should display a button with torch off image,
     * otherwise should display a torch on button
     * @param isTorchOn True if torch is on
     */
    private fun displayTorchEnabled(isTorchOn: Boolean){
        Log.i(TAG, "Torch status: $isTorchOn")
    }
    //endregion
}