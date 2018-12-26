package com.idemia.biosmart.scenes.welcome.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.idemia.biosmart.R
import android.support.design.button.MaterialButton
import android.widget.ImageView
import android.widget.TextView

class MenuCardView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs){

    init { init(context,attrs) }

    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    lateinit var buttonAction: MaterialButton
    lateinit var image: ImageView

    private fun init(context: Context, attrs: AttributeSet){
        View.inflate(context, R.layout.card_view_menu, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuCardView)
        try {
            val title = typedArray.getText(R.styleable.MenuCardView_cardTitle)
            val actionText = typedArray.getText(R.styleable.MenuCardView_cardActionTitle)
            val image = typedArray.getResourceId(R.styleable.MenuCardView_cardImage, R.drawable.ic_questions_96)
            val description = typedArray.getText(R.styleable.MenuCardView_cardDescription)
            initComponents(title, actionText, description, image)
        }finally { typedArray.recycle() }
        initComponents("", "", "", R.drawable.ic_questions_96)
    }

    private fun initComponents(title: CharSequence, actionTitle: CharSequence, description: CharSequence, cardImage: Int){
        textViewTitle = findViewById(R.id.card_view_menu_title)
        textViewDescription = findViewById(R.id.card_view_menu_description)
        buttonAction = findViewById(R.id.card_view_menu_action_title)
        image = findViewById(R.id.card_view_menu_image)

        image.setImageResource(cardImage)
        textViewTitle.text = title
        buttonAction.text = actionTitle
        textViewDescription.text = description
    }
}