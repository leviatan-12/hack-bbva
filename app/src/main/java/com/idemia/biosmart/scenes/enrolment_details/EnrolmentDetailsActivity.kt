package com.idemia.biosmart.scenes.enrolment_details

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.models.UserBiometrics
import com.idemia.biosmart.scenes.enrolment_details.view.adapters.ViewPageUserInfoAdapter
import com.idemia.biosmart.scenes.enrolment_details.view.fragments.MatchPersonToPersonDataFragment
import com.idemia.biosmart.scenes.enrolment_details.view.fragments.PersonDataFragment
import com.idemia.biosmart.utils.IDMProgress
import kotlinx.android.synthetic.main.activity_enrolment_details.*

/**
 *  EnrolmentDetails Activity
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsActivity : BaseActivity(), EnrolmentDetailsDisplayLogic {
    private lateinit var interactor: EnrolmentDetailsBusinessLogic    // Interactor
    private lateinit var router: EnrolmentDetailsRoutingLogic         // Router

    companion object { private val TAG = "EnrolmentDetailsAct" }
    override fun resourceLayoutId(): Int = R.layout.activity_enrolment_details
    override fun hideActionBar(): Boolean  = false
    override fun hideNavigationBar(): Boolean = false

    // Local Variables
    var userBiometrics: UserBiometrics? = null

    // Fragments
    private val personDataFragment = PersonDataFragment()
    private val matchPersonToPersonDataFragment = MatchPersonToPersonDataFragment()

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        retrieveUserInfo()
        button_finish.setOnClickListener {
            finish()
        }
        displayUserPhoto()
        initViewPager()
        enrolPerson()
    }

    //region A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = EnrolmentDetailsInteractor()
        this.router = EnrolmentDetailsRouter()
        val presenter = EnrolmentDetailsPresenter()
        (this.interactor as EnrolmentDetailsInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as EnrolmentDetailsRouter).setActivity(this)
    }
    //endregion

    //region USECASE - Retrive user info
    private fun retrieveUserInfo(){
        val request = EnrolmentDetailsModels.RetrieveUserInfo.Request()
        interactor.retrieveUserInfo(request)
    }

    override fun displayRetrieveUserInfo(viewModel: EnrolmentDetailsModels.RetrieveUserInfo.ViewModel) {
        Log.i(TAG, "User Biometrics info loaded from App Cache")
        userBiometrics = viewModel.userBiometrics
    }
    //endregion

    //region USECASE - Display User Photo
    private fun displayUserPhoto(){
        val request = EnrolmentDetailsModels.DisplayUserPhoto.Request()
        interactor.displayUserPhoto(request)
    }

    override fun displayUserPhoto(viewModel: EnrolmentDetailsModels.DisplayUserPhoto.ViewModel) {
        if(viewModel.photoAvailable){
            image_view_photo.setImageBitmap(viewModel.bitmap)
        }
    }
    //endregion

    //region USECASE - Enrol Person
    /** Enrol Person */
    private fun enrolPerson() {
        userBiometrics?.let { biometryInfo ->
            loader = IDMProgress(this, getString(R.string.enrolment_details_trying_enroll_new_user), getString(R.string.label_please_wait)).kProgress
            loader?.show()
            val request = EnrolmentDetailsModels.EnrolPerson.Request(this@EnrolmentDetailsActivity, biometryInfo)
            interactor.enrolPerson(request)
        }?: run{
            Toast.makeText(applicationContext, getString(R.string.enrolment_details_user_biometry_info_incomplete),Toast.LENGTH_LONG).show()
        }
    }

    override fun displayEnrolPerson(viewModel: EnrolmentDetailsModels.EnrolPerson.ViewModel) {
        Log.i(TAG, "displayEnrolPerson: ${viewModel.enrolmentResponse.message}")
        if(viewModel.enrolmentResponse.code == 200){
            onEnrolmentSuccess(viewModel.enrolmentResponse)
        }else {
            onEnrolmentFailed(viewModel.enrolmentResponse)
        }
        loader?.dismiss()
    }

    private fun onEnrolmentSuccess(enrolmentResponse: EnrolmentResponse){
        Toast.makeText(applicationContext, "Person Enrolled Successfully", Toast.LENGTH_LONG).show()
        personDataFragment.bind(enrolmentResponse)
    }

    private fun onEnrolmentFailed(enrolmentResponse: EnrolmentResponse){
        Toast.makeText(applicationContext, enrolmentResponse.message, Toast.LENGTH_LONG).show()
        when(enrolmentResponse.code){
            409 -> personDataFragment.bind(enrolmentResponse)
            else -> finish()
        }
    }
    //endregion

    //region USECASE - Display Error
    override fun displayError(viewModel: EnrolmentDetailsModels.Error.ViewModel) {
        Toast.makeText(applicationContext, viewModel.throwable.localizedMessage, Toast.LENGTH_LONG).show()
        loader?.dismiss()
    }
    //endregion

    //region UI - Init View Pager
    private fun initViewPager(){
        val adapter  = ViewPageUserInfoAdapter(supportFragmentManager)
        adapter.addFragment(personDataFragment, getString(R.string.person_data_fragment_label_general_user_info))
        adapter.addFragment(matchPersonToPersonDataFragment, getString(R.string.person_data_fragment_label_match_person_to_person_process))
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
    //endregion
}

/**
 *  EnrolmentDetails Display Logic
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface EnrolmentDetailsDisplayLogic {
    fun displayRetrieveUserInfo(viewModel: EnrolmentDetailsModels.RetrieveUserInfo.ViewModel)
    fun displayUserPhoto(viewModel: EnrolmentDetailsModels.DisplayUserPhoto.ViewModel)
    fun displayEnrolPerson(viewModel: EnrolmentDetailsModels.EnrolPerson.ViewModel)
    fun displayError(viewModel: EnrolmentDetailsModels.Error.ViewModel)
}