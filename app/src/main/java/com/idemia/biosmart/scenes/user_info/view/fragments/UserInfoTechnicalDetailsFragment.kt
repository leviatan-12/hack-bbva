package com.idemia.biosmart.scenes.user_info.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import kotlinx.android.synthetic.main.fragment_userinfo_technical_details.*

class UserInfoTechnicalDetailsFragment: Fragment() {
    lateinit var mView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_userinfo_technical_details, container, false)
        return mView
    }

    private fun onLoadFragment(){
        text_view_enrolment_duration.text = getString(R.string.label_NA)
        text_view_encoding_duration.text = getString(R.string.label_NA)
        text_view_match_person_to_person_duration.text = getString(R.string.label_NA)
    }

    private fun dataBinding(){
        text_view_enrolment_duration.text = getString(R.string.label_NA)
        text_view_encoding_duration.text = getString(R.string.label_NA)
        text_view_match_person_to_person_duration.text = getString(R.string.label_NA)
    }
}