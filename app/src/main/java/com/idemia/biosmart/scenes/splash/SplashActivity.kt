package com.idemia.biosmart.scenes.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.idemia.biosmart.R
import com.idemia.morphobiosmart.android.BaseActivity
import com.idemia.biosmart.scenes.welcome.WelcomeActivity

class SplashActivity : BaseActivity() {
    private val handler = Handler()

    // Mandatory methods
    override fun resourceLayoutId(): Int = R.layout.activity_splash
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = true

    override fun inject() {
        // Do nothing :)
    }

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        handler.postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 1000)
    }
}
