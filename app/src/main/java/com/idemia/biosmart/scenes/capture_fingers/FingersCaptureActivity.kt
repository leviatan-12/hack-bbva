package com.idemia.biosmart.scenes.capture_fingers

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.fingers.FingersActivity
import com.idemia.biosmart.utils.AppCache
import kotlinx.android.synthetic.main.activity_capture_fingers.*

class FingersCaptureActivity : FingersActivity() {

    companion object {
        val TAG = "FingersCaptureActivity"
    }

    override fun resourceLayoutId(): Int = R.layout.activity_capture_fingers
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = false
    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        super.onLoadActivity(savedInstanceState)
        initUi()
    }

    /**
     * When SDK is ready for capture, this method will be executed
     */
    override fun readyForCapture() {
        // TODO: Add a countdown to start capture
        startCapture()
    }

    override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel) {
        text_view_feedback_info.text = viewModel.message
    }

    override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel) {
        Toast.makeText(applicationContext, getString(R.string.label_capture_finished) ,Toast.LENGTH_LONG).show()
        uiOnSuccess()
    }

    override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel) {
        viewModel.morphoImages?.let { imageList ->
            // TODO: Verify hand position
            AppCache.imageListLeft = imageList
            AppCache.imageListRight = imageList
        }
    }

    override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel) {
        Log.e(TAG, "${viewModel.captureError?.name} - Error code: ${viewModel.captureError?.ordinal}")
        viewModel.captureError?.let { error ->
            Toast.makeText(applicationContext, getString(R.string.label_error, error.name), Toast.LENGTH_LONG).show()
        }
    }

    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        Log.e(TAG, "displayError: ${viewModel.throwable.localizedMessage}")
        Toast.makeText(applicationContext,
            viewModel.throwable.localizedMessage + " - Please verify your license status",
            Toast.LENGTH_LONG).show()
        uiOnError()
    }

    private fun initUi(){
        text_view_feedback_info.visibility = View.VISIBLE
        button_finish.visibility = View.GONE
        button_finish.setOnClickListener {
            finish()
        }
    }

    private fun uiOnSuccess(){
        text_view_feedback_info.visibility = View.GONE
        button_finish.visibility = View.VISIBLE
        setResult(Activity.RESULT_OK)
    }

    private fun uiOnError(){
        text_view_feedback_info.visibility = View.GONE
        button_finish.visibility = View.VISIBLE
        setResult(Activity.RESULT_CANCELED)
    }
}
