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
        val recyclerView = recycle_view
        val adapter = RecyclerViewAdapter()
        text_view_no_hit_rank.text = getString(R.string.person_to_person_data_fragment_no_hit_rank, 0)
        adapter.canditates = arrayListOf(
            Candidate("NO_HIT",false,"Test_ID",3400),
            Candidate("HIT",false,"Test_ID2",3600),
            Candidate("NO_HIT",false,"Test_ID3",1000),
            Candidate("NO_HIT",false,"Test_ID4",1277),
            Candidate("NO_HIT",false,"Test_ID5",2344),
            Candidate("NO_HIT",false,"Test_ID6",1293),
            Candidate("NO_HIT",false,"Test_ID7",4456),
            Candidate("NO_HIT",false,"Test_ID8",23),
            Candidate("NO_HIT",false,"Test_ID9",4555),
            Candidate("NO_HIT",false,"Test_ID10",10000))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(mView.context)
    }
}