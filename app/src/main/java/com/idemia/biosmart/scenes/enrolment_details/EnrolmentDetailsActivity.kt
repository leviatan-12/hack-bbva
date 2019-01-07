package com.idemia.biosmart.scenes.enrolment_details

import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.models.UserBiometrics
import com.idemia.biosmart.scenes.enrolment_details.view.adapters.ViewPageUserInfoAdapter
import com.idemia.biosmart.scenes.enrolment_details.view.fragments.MatchPersonToPersonDataFragment
import com.idemia.biosmart.scenes.enrolment_details.view.fragments.PersonDataFragment
import com.idemia.biosmart.utils.IDMProgress
import com.kaopiz.kprogresshud.KProgressHUD
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

    // Fragments
    private val personDataFragment = PersonDataFragment()
    private val matchPersonToPersonDataFragment = MatchPersonToPersonDataFragment()

    override fun onLoadActivity() {
        initViewPager()
    }

    override fun inject() {
        val activity = this
        this.interactor = EnrolmentDetailsInteractor()
        this.router = EnrolmentDetailsRouter()
        val presenter = EnrolmentDetailsPresenter()
        (this.interactor as EnrolmentDetailsInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as EnrolmentDetailsRouter).setActivity(this)
    }

    //region USECASE: Enrol Person
    /** Enrol Person */
    private fun enrolPerson() {
        loader = IDMProgress(this, "Trying to enrol new user", "Please wait...").kProgress
        loader?.show()

        // TODO: Add biometry info
        val userBiometrics = UserBiometrics("tester",1,false)
        val request = EnrolmentDetailsModels.EnrolPerson.Request(this@EnrolmentDetailsActivity, userBiometrics)
        interactor.enrolPerson(request)
    }

    override fun displayEnrolPerson(viewModel: EnrolmentDetailsModels.EnrolPerson.ViewModel) {
        Log.i(TAG, "displayEnrolPerson: ${viewModel.enrolmentResponse.message}")
        Toast.makeText(applicationContext, "Person Enrolled Successfully", Toast.LENGTH_LONG).show()
        loader?.dismiss()
    }
    //endregion

    //region UI: Init View Pager
    private fun initViewPager(){
        val adapter  = ViewPageUserInfoAdapter(supportFragmentManager)
        adapter.addFragment(personDataFragment, "General User Info")
        adapter.addFragment(matchPersonToPersonDataFragment, "Match Person to Person Details")
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
    fun displayEnrolPerson(viewModel: EnrolmentDetailsModels.EnrolPerson.ViewModel)
}