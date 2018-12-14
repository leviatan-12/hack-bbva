package com.idemia.biosmart

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class BioSmartApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/GoogleSans-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build())
    }
}