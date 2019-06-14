package com.idemia.biosmart.scenes.welcome.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.scenes.welcome.WelcomeModels
import kotlinx.android.synthetic.main.card_view_menu.view.*

class CardsMenuAdapter(var list: ArrayList<WelcomeModels.CardMenu>): RecyclerView.Adapter<CardsMenuAdapter.Companion.MenuCardHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MenuCardHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view_menu, viewGroup,false)
        return MenuCardHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(menuCardHolder: MenuCardHolder, index: Int) {
        menuCardHolder.actionTitle.text = list[index].actionTitle
        menuCardHolder.title.text = list[index].title
        menuCardHolder.image.setImageResource(list[index].image)
        menuCardHolder.addOnClickListener(list[index].listener)
        menuCardHolder.description.text = list[index].description
    }

    companion object {
        class MenuCardHolder(val view: View): RecyclerView.ViewHolder(view) {
            val image = view.card_view_menu_image
            val title = view.card_view_menu_title
            val description = view.card_view_menu_description
            var actionTitle = view.card_view_menu_action_title

            fun addOnClickListener(action: View.OnClickListener?){
                actionTitle.setOnClickListener(action)
            }
        }
    }
}