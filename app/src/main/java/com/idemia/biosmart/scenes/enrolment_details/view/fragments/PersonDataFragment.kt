package com.idemia.biosmart.scenes.enrolment_details.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.models.EnrolmentResponse
import kotlinx.android.synthetic.main.fragment_person_data.*

class PersonDataFragment: Fragment() {
    lateinit var mView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_person_data, container, false)
        return mView
    }


    private fun onLoadFragment(){
        text_view_enrol_error_code.text = getString(R.string.label_NA)
        text_view_enrol_message.text = getString(R.string.label_NA)
        text_view_enrol_duration.text = getString(R.string.label_NA)

        text_view_encode_error_code.text = getString(R.string.label_NA)
        text_view_encode_message.text = getString(R.string.label_NA)
        text_view_encode_duration.text = getString(R.string.label_NA)
    }

    fun bind(enrolmentResponse: EnrolmentResponse){
        enrolmentResponse.enrollPerson?.let{ enrollPerson ->
            text_view_enrol_error_code.text = enrollPerson.errorCode
            text_view_enrol_message.text = enrollPerson.message
            text_view_enrol_duration.text = getString(R.string.label_ms, enrollPerson.duration)
        }
        text_view_encode_error_code.text = enrolmentResponse.encodePerson.errorCode
        text_view_encode_message.text = enrolmentResponse.encodePerson.message
        text_view_encode_duration.text = getString(R.string.label_ms, enrolmentResponse.encodePerson.duration)
    }
}