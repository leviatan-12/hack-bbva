package com.idemia.biosmart.scenes.capture_face

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.bio_smart.capture.CaptureModels
import com.idemia.biosmart.base.bio_smart.face.FaceCaptureActivity

class CaptureFaceActivity : FaceCaptureActivity() {
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false
    override fun resourceLayoutId(): Int = R.layout.activity_capture_face

    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    override fun readyForCapture() {

    }

    override fun displayError(viewModel: CaptureModels.Error.ViewModel) {
        Toast.makeText(applicationContext, "Error due: ${viewModel.throwable.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}
