package com.idemia.morphobiosmart.utils

import android.content.Context
import com.idemia.morphobiosmart.utils.DisposableManager
import com.kaopiz.kprogresshud.KProgressHUD

class IDMProgress(context: Context, label: String, details: String) {

    var kProgress: KProgressHUD

    init {
        kProgress = create(context, label, details)
        kProgress.setCancellable {
            DisposableManager.clear()
        }
    }

    private fun create(context: Context, label: String, details: String): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(label)
            .setDetailsLabel(details)
            .setDimAmount(0.85f)
            .setCancellable(true)
    }
}