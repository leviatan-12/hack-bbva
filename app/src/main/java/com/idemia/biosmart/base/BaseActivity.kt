package com.idemia.biosmart.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity: AppCompatActivity(){

    /** Layout Resource to set*/
    @LayoutRes abstract fun resourceLayoutId(): Int

    /** Set to True to hide action bar, otherwise false **/
    abstract fun hideActionBar() : Boolean

    /** Set to True to hide navigation bar, otherwise false **/
    abstract fun hideNavigationBar() : Boolean

    /** Setup all environment for this feature */
    abstract fun inject()

    /** Use this method to load all UI Elements, add functions, and so on...*/
    abstract fun onLoadActivity()

    /**
     * On Create Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(hideActionBar()){ disableActionBarFromApp() }
        if(hideNavigationBar()) { hideNavigationBarFromApp() }
        setContentView(resourceLayoutId())
        inject()
        onLoadActivity()
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
}