package com.idemia.biosmart.scenes.capture_fingers

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.fingers.FingersActivity

class CaptureFingersActivity : FingersActivity() {

    override fun resourceLayoutId(): Int = R.layout.activity_capture_fingers
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = true

    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view


    /**
     * When SDK is ready for capture, this method will be executed
     */
    override fun readyForCapture() {

    }

    override fun displayCaptureInfo(viewModel: CaptureModels.CaptureInfo.ViewModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayCaptureFinish(viewModel: CaptureModels.CaptureFinish.ViewModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayCaptureSuccess(viewModel: CaptureModels.CaptureSuccess.ViewModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayCaptureFailure(viewModel: CaptureModels.CaptureFailure.ViewModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        Toast.makeText(applicationContext, "Error: ${viewModel.throwable.localizedMessage}", Toast.LENGTH_LONG)
            .show()
    }
}
