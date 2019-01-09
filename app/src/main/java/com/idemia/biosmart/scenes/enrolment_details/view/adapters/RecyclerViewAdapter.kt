package com.idemia.biosmart.scenes.enrolment_details.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.models.Candidate
import kotlinx.android.synthetic.main.layout_list_canditate_item.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    var canditates: ArrayList<Candidate> = ArrayList()

    companion object {
        val TAG = "RecyclerViewAdapter"
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val score = itemView.text_view_score
        val personId = itemView.text_view_person_id
        val decision = itemView.text_view_decision
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_canditate_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = canditates.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.score.text = canditates[position].score.toString()
        viewHolder.decision.text = canditates[position].desicion
        viewHolder.personId.text = canditates[position].id
    }
}