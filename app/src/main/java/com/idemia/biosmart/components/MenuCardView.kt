package com.idemia.biosmart.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.idemia.biosmart.R
import android.support.annotation.StyleableRes
import android.support.design.button.MaterialButton
import android.widget.ImageView
import android.widget.TextView


class MenuCardView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs){

    init { init(context,attrs) }

    @StyleableRes val index0:Int = 0
    @StyleableRes val index1:Int = 1
    @StyleableRes val index2:Int = 2

    lateinit var textViewTitle: TextView
    lateinit var buttonAction: MaterialButton
    lateinit var image: ImageView

    fun init(context: Context, attrs: AttributeSet){
        View.inflate(context, R.layout.card_view_menu, this)
        val sets = intArrayOf(R.attr.cardTitle, R.attr.cardActionTitle, R.attr.cardImage)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuCardView)
        var title: CharSequence = ""
        var actionText: CharSequence = ""
        var image = R.drawable.ic_questions_96
        try {
            title = typedArray.getText(R.styleable.MenuCardView_cardTitle)
            actionText = typedArray.getText(R.styleable.MenuCardView_cardActionTitle)
            image = typedArray.getResourceId(R.styleable.MenuCardView_cardImage, R.drawable.ic_questions_96)
        }finally {
            typedArray.recycle()
        }

        initComponents(title, actionText, image)
    }

    fun initComponents(title: CharSequence, actionTitle: CharSequence, cardImage: Int){
        textViewTitle = findViewById(R.id.card_view_menu_title)
        buttonAction = findViewById(R.id.card_view_menu_action_title)
        image = findViewById(R.id.card_view_menu_image)

        image.setImageResource(cardImage)
        textViewTitle.text = title
        buttonAction.text = actionTitle
    }
}