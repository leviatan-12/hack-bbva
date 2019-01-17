package com.idemia.biosmart.base.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import android.util.AttributeSet
import android.view.View

class FaceIdMask: View {
    var paint = Paint()

    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrSet: AttributeSet): super(context, attrSet){
        init()
    }

    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int): super(context, attrSet, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), 450F, paint)
    }

    private fun init() {
        paint = Paint()
        paint.color = Color.BLUE
        paint.strokeWidth = 50F
        paint.style = Paint.Style.STROKE
    }
}