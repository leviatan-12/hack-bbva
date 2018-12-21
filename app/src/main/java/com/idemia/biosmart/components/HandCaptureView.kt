package com.idemia.biosmart.components

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.idemia.biosmart.R

class HandCaptureView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    init { init(context,attrs) }

    fun init(context: Context, attrs: AttributeSet){
        View.inflate(context, R.layout.hand_capture_view, this)
    }
}