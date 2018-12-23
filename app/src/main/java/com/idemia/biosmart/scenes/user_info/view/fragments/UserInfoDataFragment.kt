package com.idemia.biosmart.scenes.user_info.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.scenes.user_info.UserInfoModels
import kotlinx.android.synthetic.main.fragment_userinfo_data.*

class UserInfoDataFragment: Fragment(){
    lateinit var mView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_userinfo_data, container, false)
        return mView
    }

    private fun onLoadFragment(){
        text_view_username.text = getString(R.string.label_NA)
        text_view_name.text = getString(R.string.label_NA)
        text_view_last_name.text = getString(R.string.label_NA)
        text_view_m_last_name.text = getString(R.string.label_NA)
    }

    fun dataBinding(user: UserInfoModels.User?){
        user?.let {
            text_view_username.text = it.username
            text_view_name.text = it.name
            text_view_last_name.text = it.last_name
            text_view_m_last_name.text = it.m_last_name
        }
    }
}