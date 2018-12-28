package com.idemia.biosmart.scenes.capture_fingers

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.fingers.FingersActivity
import com.idemia.biosmart.base.fingers.FingersModels

class CaptureFingersActivity : FingersActivity() {

    override fun resourceLayoutId(): Int = R.layout.activity_capture_fingers
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = true

    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    /**
     * When SDK is ready for capture, this method will be executed
     */
    override fun readyForCapture() {
        startCapture()
    }

    override fun displayError(viewModel: FingersModels.Error.ViewModel) {
        Toast.makeText(applicationContext, "Error: ${viewModel.throwable.localizedMessage}", Toast.LENGTH_LONG)
            .show()
    }
}
