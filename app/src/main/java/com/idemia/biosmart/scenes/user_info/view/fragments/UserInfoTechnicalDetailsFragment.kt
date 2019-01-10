package com.idemia.biosmart.scenes.user_info.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.models.AuthenticationResponse
import kotlinx.android.synthetic.main.fragment_userinfo_technical_details.*

class UserInfoTechnicalDetailsFragment: Fragment() {
    lateinit var mView: View
    var authenticationResponse: AuthenticationResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_userinfo_technical_details, container, false)
        return mView
    }

    private fun onLoadFragment(){
        text_view_encode_duration.text = getString(R.string.label_NA)
        text_view_encode_error_code.text = getString(R.string.label_NA)
        text_view_encode_message.text = getString(R.string.label_NA)

        text_view_authenticate_duration.text = getString(R.string.label_NA)
        text_view_authenticate_error_code.text = getString(R.string.label_NA)
        text_view_authenticate_message.text = getString(R.string.label_NA)

        authenticationResponse?.let { bind(it) }
    }

    fun bind(authenticationResponse: AuthenticationResponse){
        this.authenticationResponse = authenticationResponse
        text_view_encode_duration.text = authenticationResponse.encodePerson?.duration.toString()
        text_view_encode_error_code.text = authenticationResponse.encodePerson?.errorCode
        text_view_encode_message.text = authenticationResponse.encodePerson?.message

        text_view_authenticate_duration.text = authenticationResponse.authenticatePerson?.duration.toString()
        text_view_authenticate_error_code.text = authenticationResponse.authenticatePerson?.errorCode
        text_view_authenticate_message.text = authenticationResponse.authenticatePerson?.message
    }
}