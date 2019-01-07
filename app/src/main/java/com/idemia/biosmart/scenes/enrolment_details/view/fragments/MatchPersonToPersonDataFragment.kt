package com.idemia.biosmart.scenes.enrolment_details.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R

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

    }
}