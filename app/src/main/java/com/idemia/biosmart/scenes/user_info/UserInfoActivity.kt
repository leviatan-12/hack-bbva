package com.idemia.biosmart.scenes.user_info

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.utils.Base64
import kotlinx.android.synthetic.main.activity_userinfo.*
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.idemia.biosmart.models.Candidate
import com.idemia.biosmart.scenes.enrolment_details.view.fragments.MatchPersonToPersonDataFragment
import com.idemia.biosmart.scenes.user_info.view.adapters.ViewPageUserInfoAdapter
import com.idemia.biosmart.scenes.user_info.view.fragments.UserInfoDataFragment
import com.idemia.biosmart.scenes.user_info.view.fragments.UserInfoTechnicalDetailsFragment
import com.idemia.biosmart.utils.IDMProgress

/**
 *  UserInfo Activity
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoActivity : BaseActivity(), UserInfoDisplayLogic {
    private lateinit var interactor: UserInfoBusinessLogic    // Interactor
    private lateinit var router: UserInfoRoutingLogic         // Router

    companion object {
        private val TAG = "UserInfoActivity"
        const val AUTHENTICATE_USER = 0x01
        const val IDENTIFY_USER = 0x02
        const val KEY_OPERATION_TYPE = "OPERATION_TYPE"
    }

    override fun resourceLayoutId(): Int = R.layout.activity_userinfo
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false
    val viewPageUserInfoAdapter = ViewPageUserInfoAdapter(supportFragmentManager)

    //region VARS - Local variables
    private val userInfoDataFragment = UserInfoDataFragment()
    private val userInfoTechnicalDetailsFragment = UserInfoTechnicalDetailsFragment()
    private val matchPersonToPersonDataFragment = MatchPersonToPersonDataFragment()
    //endregion

    //region BASE ACTIVITY - On load activity
    override fun onLoadActivity(savedInstanceState: Bundle?) {
        val operation = intent.getIntExtra(KEY_OPERATION_TYPE, 0x00)
        verifyOperationType(operation)
    }
    //endregion

    //region BASE ACTIVITY - A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = UserInfoInteractor()
        this.router = UserInfoRouter()
        val presenter = UserInfoPresenter()
        (this.interactor as UserInfoInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as UserInfoRouter).setActivity(this)
    }
    //endregion

    //region USECASE - Authenticate user
    private fun authenticateUser(){
        loader = IDMProgress(this, "Authenticating Person", getString(R.string.label_please_wait)).kProgress
        loader?.show()
        Log.i(TAG, "authenticateUser")
        val request = UserInfoModels.AuthenticateUser.Request(this@UserInfoActivity)
        interactor.authenticateUser(request)
    }

    override fun displayAuthenticateUser(viewModel: UserInfoModels.AuthenticateUser.ViewModel) {
        // TODO: Delete this Binding, just for testing
        matchPersonToPersonDataFragment.bind(arrayListOf(Candidate("HIT",false,"Sample",3500)))
        when(viewModel.authenticationResponse.code){
            200 -> {
                val candidateId = viewModel.authenticationResponse.personId
                userInfoTechnicalDetailsFragment.bind(viewModel.authenticationResponse)
                loader?.dismiss()
                search(candidateId!!)
            }
            400 -> {
                showToast(getString(R.string.fatal_user_biometry_info_incomplete))
                loader?.dismiss()
            }
            404 -> {
                showToast(viewModel.authenticationResponse.authenticatePerson!!.message)
                userInfoTechnicalDetailsFragment.bind(viewModel.authenticationResponse)
                loader?.dismiss()
            }
            else -> {
                showToast(getString(R.string.fatal_unknown_error, "Error on displayAuthenticateUser() method"))
                loader?.dismiss()
            }
        }
        Log.i(TAG, "displayAuthenticateUser: ${viewModel.authenticationResponse.message}")
        search("alfredo")
    }
    //endregion

    //region USECASE - Identify User
    private fun identifyUser(){
        loader = IDMProgress(this, "Identifying Person", getString(R.string.label_please_wait)).kProgress
        loader?.show()
        Log.i(TAG, "identifyUser")
        val request = UserInfoModels.IdentifyUser.Request(this@UserInfoActivity)
        interactor.identifyUser(request)
    }

    override fun displayIdentifyUser(viewModel: UserInfoModels.IdentifyUser.ViewModel) {
        // TODO: Delete this Binding, just for testing
        matchPersonToPersonDataFragment.bind(arrayListOf(Candidate("HIT",false,"Sample",3500)))
        when(viewModel.identifyResponse.code){
            200 -> {
                val candidateId = viewModel.identifyResponse.matchPersonToPerson!!.candidates[0].id
                matchPersonToPersonDataFragment.bind(viewModel.identifyResponse.matchPersonToPerson.candidates)
                search(candidateId)
            }
            400 -> {
                showToast(getString(R.string.fatal_user_biometry_info_incomplete))
                loader?.dismiss()
            }
            404 -> {
                showToast(viewModel.identifyResponse.message)
                userInfoTechnicalDetailsFragment.bind(viewModel.identifyResponse)
                loader?.dismiss()
            }
            else -> {
                showToast(getString(R.string.fatal_unknown_error, "Error on displayIdentifyUser() method"))
                loader?.dismiss()
            }
        }
        loader?.dismiss()
        search("alfredo")
    }
    //endregion

    //region USECASE - Search User in DB
    /**
     * Search User in DB
     */
    private fun search(username: String) {
        loader = IDMProgress(this, "Getting User Info", "Please Wait...").kProgress
        loader?.show()
        val searchPersonRequest = UserInfoModels.SearchPersonRequest(username, 1)
        val request = UserInfoModels.Search.Request(this@UserInfoActivity, searchPersonRequest)
        interactor.search(request)
    }

    override fun displaySearch(viewModel: UserInfoModels.Search.ViewModel) {
        if(viewModel.userFound){
            displaySearchSuccess(getString(R.string.message_user_found), viewModel.user!!)
        }else{
            displaySearchNotFound(getString(R.string.message_user_not_found))
        }
    }

    private fun displaySearchSuccess(message: String, user: UserInfoModels.User){
        Toast.makeText(applicationContext, message.toLowerCase().capitalize(), Toast.LENGTH_LONG).show()
        user.photo?.let {
            val photo = Base64.decode(user.photo)
            val options = BitmapFactory.Options()
            options.inMutable = true
            val bmp = BitmapFactory.decodeByteArray(photo, 0, photo.size, options)
            image_view_photo.setImageBitmap(bmp)
        }
        userInfoDataFragment.dataBinding(user)
        loader?.dismiss()
    }

    private fun displaySearchNotFound(message: String){
        Toast.makeText(applicationContext, message.toLowerCase().capitalize(), Toast.LENGTH_LONG).show()
        userInfoDataFragment.dataBinding(null)
        loader?.dismiss()
    }
    //endregion

    //region USECASE - Display Error
    override fun displayError(viewModel: UserInfoModels.Error.ViewModel) {
        Toast.makeText(applicationContext, viewModel.throwable.localizedMessage, Toast.LENGTH_LONG).show()
        loader?.dismiss()
    }
    //endregion

    //region UI - Init view pager
    private fun initViewPager(withMatchPersonToPersonTab: Boolean){
        viewPageUserInfoAdapter.addFragment(userInfoDataFragment, getString(R.string.user_info_label_user_info))
        viewPageUserInfoAdapter.addFragment(userInfoTechnicalDetailsFragment, getString(R.string.user_info_label_technical_details))
        if(withMatchPersonToPersonTab){
            viewPageUserInfoAdapter.addFragment(matchPersonToPersonDataFragment, getString(R.string.user_info_label_match_person_to_person_process))
        }
        view_pager.adapter = viewPageUserInfoAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
    //endregion

    private fun verifyOperationType(type: Int){
        when(type){
            AUTHENTICATE_USER -> {
                authenticateUser()
                initViewPager(true)
            }
            IDENTIFY_USER -> {
                identifyUser()
                initViewPager(false)
            }
            else -> showToast(getString(R.string.fatal_invalid_operation))
        }
    }
}

/**
 *  UserInfo Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface UserInfoDisplayLogic {
    fun displayAuthenticateUser(viewModel: UserInfoModels.AuthenticateUser.ViewModel)
    fun displayIdentifyUser(viewModel: UserInfoModels.IdentifyUser.ViewModel)
    fun displaySearch(viewModel: UserInfoModels.Search.ViewModel)
    fun displayError(viewModel: UserInfoModels.Error.ViewModel)
}