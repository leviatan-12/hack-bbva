package com.idemia.biosmart.scenes.capture_face

import android.os.Bundle
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.face.FaceCaptureActivity

class FaceCaptureActivity : FaceCaptureActivity() {
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false
    override fun resourceLayoutId(): Int = R.layout.activity_capture_face

    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    override fun readyForCapture() {

    }

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        super.onLoadActivity(savedInstanceState)
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
        Toast.makeText(applicationContext, "Error due: ${viewModel.throwable.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}
