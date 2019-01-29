package com.idemia.biosmart.scenes.enrolment_details.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.models.Candidate
import com.idemia.biosmart.scenes.enrolment_details.view.adapters.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_match_person_to_person_data.*

class MatchPersonToPersonDataFragment: Fragment(){
    lateinit var mView: View
    val adapter = RecyclerViewAdapter()
    var candidates: ArrayList<Candidate> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_match_person_to_person_data, container, false)
        return mView
    }


    private fun onLoadFragment(){
        initRecyclerView()
    }

    private fun initRecyclerView(){
        text_view_no_hit_rank.text = getString(R.string.person_to_person_data_fragment_no_hit_rank, 0)
        val recyclerView = recycle_view
        adapter.canditates = arrayListOf()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(mView.context)
        if (candidates.size > 0 ){
            bind(candidates, 0)
        }
    }

    fun bind(candidates: List<Candidate>, noHitRank: Int){
        this.candidates = candidates as ArrayList<Candidate>
        adapter.canditates.clear()
        adapter.canditates.addAll(candidates)
        adapter.notifyDataSetChanged()
        text_view_no_hit_rank.text = getString(R.string.person_to_person_data_fragment_no_hit_rank, noHitRank)
    }
}