package com.idemia.biosmart.base.android

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.scenes.capture_fingers.FingersCaptureActivity
import com.kaopiz.kprogresshud.KProgressHUD
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity: AppCompatActivity(){

    companion object {
        const val TAG = "BaseActivity"
    }

    /** Preference Manager */
    lateinit var preferenceManager: SharedPreferences

    /** Layout Resource to set*/
    @LayoutRes abstract fun resourceLayoutId(): Int

    /** Set to True to hide action bar, otherwise false **/
    abstract fun hideActionBar() : Boolean

    /** Set to True to hide navigation bar, otherwise false **/
    abstract fun hideNavigationBar() : Boolean

    /** Setup all environment for this feature */
    abstract fun inject()

    /** Use this method to load all UI Elements, add functions, and so on...*/
    abstract fun onLoadActivity(savedInstanceState: Bundle?)

    /** A [KProgressHUD] Loader */
    var loader: KProgressHUD? = null

    /**
     * On Create Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(hideActionBar()){ disableActionBarFromApp() }
        if(hideNavigationBar()) { hideNavigationBarFromApp() }
        setContentView(resourceLayoutId())
        inject()
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this)
        onLoadActivity(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
        loader?.dismiss()
        loader = null
    }

    override fun onDestroy() {
        super.onDestroy()
        DisposableManager.clear()
    }

    /**
     * Attach Base Context
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    /**
     * Hide both the navigation bar and the status bar.
     * SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
     * a general rule, you should design your app to hide the status bar whenever you
     * hide the navigation bar.
     * */
    private fun hideNavigationBarFromApp(){
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun disableActionBarFromApp() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    protected fun showToast(message: String, duration: Int = Toast.LENGTH_LONG){
        runOnUiThread {
            Toast.makeText(applicationContext, message, duration).show()
        }
    }

    /**
     * Countdown timer
     * @param millisInFuture Start from
     * @param countDownInterval Countdown interval
     * @param updateUi A closure to update your UI
     * @param finish A closure to call when countdown finish
     */
    protected fun createCountdownTimer(millisInFuture: Long,
                                      countDownInterval: Long = 1000,
                                      updateUi: (value: Int) -> Any ,
                                      finish: () -> Any ): CountDownTimer{
        var initValue= (millisInFuture / 1000).toInt()

        return object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(FingersCaptureActivity.TAG, "onTick: $millisUntilFinished - $initValue")
                updateUi(initValue--)
            }

            override fun onFinish() {
                Log.i(FingersCaptureActivity.TAG,"onFinish ")
                finish()
            }
        }
    }
}