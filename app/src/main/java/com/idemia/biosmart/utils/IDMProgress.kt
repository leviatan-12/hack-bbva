package com.idemia.biosmart.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

class IDMProgress {
    companion object {
        fun create(context: Context): KProgressHUD{
            return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Obtaining data")
                .setCancellable(false)
        }
    }
}