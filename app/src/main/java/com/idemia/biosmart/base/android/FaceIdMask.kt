package com.idemia.biosmart.base.android

import android.content.Context
import android.graphics.*

import android.util.AttributeSet
import android.view.View
import android.graphics.Bitmap

class FaceIdMask: View {
    //region VARS
    private var circleMargin = 30
    private var cv: Canvas? = null
    private var paint = Paint()
    private var bm: Bitmap? = null
    //endregion

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
        val w = width
        val h = height
        val radius = (if (w > h) h / 2 else w / 2) - circleMargin

        bm!!.eraseColor(Color.TRANSPARENT)
        cv!!.drawColor(Color.parseColor("#EB000000")) // EB = 235
        cv!!.drawCircle((w / 2).toFloat(), (h / 2).toFloat(), radius.toFloat(), paint)
        canvas?.drawBitmap(bm!!, 0F, 0F, null)
        super.onDraw(canvas)
    }

    private fun init() {
        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.isAntiAlias = true
        //val dashPath = DashPathEffect(floatArrayOf(5f, 5f), 1.0.toFloat())
        //paint.pathEffect = dashPath
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) // Create bitmap
            cv = Canvas(bm!!)   // create canvas
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }
}