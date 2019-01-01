package com.idemia.biosmart.scenes.capture_fingers

import android.Manifest
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.fingers.FingersActivity
import com.idemia.biosmart.base.fingers.FingersModels
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class CaptureFingersActivity : FingersActivity() {

    override fun resourceLayoutId(): Int = R.layout.activity_capture_fingers
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = true

    override fun surfaceViewLayout(): Int = R.id.morpho_surface_view

    // Listener for permission
    private val listener = object : PermissionListener {
        override fun onPermissionGranted(response: PermissionGrantedResponse) {
            startCapture()
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse) {
            Toast.makeText(applicationContext, "Camera Permission was denied", Toast.LENGTH_LONG).show()
        }

        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
            Toast.makeText(applicationContext, "onPermissionRationaleShouldBeShown", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * When SDK is ready for capture, this method will be executed
     */
    override fun readyForCapture() {
        requestForCameraPermission()
    }

    override fun displayError(viewModel: FingersModels.Error.ViewModel) {
        Toast.makeText(applicationContext, "Error: ${viewModel.throwable.localizedMessage}", Toast.LENGTH_LONG)
            .show()
    }

    private fun requestForCameraPermission(){
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(listener).check()
    }
}
