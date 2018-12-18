package com.idemia.biosmart.scenes.user_info

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity
import com.idemia.biosmart.utils.Base64
import kotlinx.android.synthetic.main.activity_userinfo.*
import android.graphics.BitmapFactory
import com.idemia.biosmart.utils.IDMProgress
import com.kaopiz.kprogresshud.KProgressHUD

/**
 *  UserInfo Activity
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoActivity : BaseActivity(), UserInfoDisplayLogic {
    private lateinit var interactor: UserInfoBusinessLogic    // Interactor
    private lateinit var router: UserInfoRoutingLogic         // Router

    companion object { private val TAG = "UserInfoActivity" }

    override fun resourceLayoutId(): Int = R.layout.activity_userinfo
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    lateinit var loader: KProgressHUD

    override fun onLoadActivity() {
        displayInitStatus()
        // TODO: Call this to search user after authenticate or identify user
        search("alfredo")
    }

    override fun inject() {
        val activity = this
        this.interactor = UserInfoInteractor()
        this.router = UserInfoRouter()
        val presenter = UserInfoPresenter()
        (this.interactor as UserInfoInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as UserInfoRouter).setActivity(this)
    }

    //region Search User in DB
    /**
     * Search User in DB
     */
    private fun search(username: String) {
        loader.show()
        val request = UserInfoModels.Search.Request(username,1)
        interactor.search(request)
    }

    override fun displaySearch(viewModel: UserInfoModels.Search.ViewModel) {
        if(viewModel.userFound){
            displaySearchSuccess(getString(R.string.message_user_found), viewModel.user!!)
        }else{
            displaySearchNotFound(getString(R.string.message_user_not_found))
        }
        loader.dismiss()
    }

    private fun displaySearchSuccess(message: String, user: UserInfoModels.User){
        Toast.makeText(applicationContext, message.toLowerCase().capitalize(), Toast.LENGTH_LONG).show()
        bindUserInfo(user)
        bindTechnicalDetails()
    }

    private fun displaySearchNotFound(message: String){
        Toast.makeText(applicationContext, message.toLowerCase().capitalize(), Toast.LENGTH_LONG).show()
        bindUserInfo(null)
        bindTechnicalDetails()
    }

    private fun bindUserInfo(user: UserInfoModels.User?){
        user?.let {
            user.photo?.let {
                val photo = Base64.decode(user.photo)
                val options = BitmapFactory.Options()
                options.inMutable = true
                val bmp = BitmapFactory.decodeByteArray(photo, 0, photo.size, options)
                image_view_photo.setImageBitmap(bmp)
            }
            text_view_username.text = "@${it.username}"
            text_view_name.text = it.name
            text_view_last_name.text = it.last_name
            text_view_m_last_name.text = it.m_last_name
        }
    }

    private fun bindTechnicalDetails(){
        text_view_enrolment_duration.text = getString(R.string.label_NA)
        text_view_encoding_duration.text = getString(R.string.label_NA)
        text_view_match_person_to_person_duration.text = getString(R.string.label_NA)
    }
    //endregion

    //region Display Error
    override fun displayError(viewModel: UserInfoModels.Error.ViewModel) {
        Toast.makeText(applicationContext, viewModel.throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }
    //endregion

    /**
     * Initial Status View UI
     */
    private fun displayInitStatus(){
        image_view_photo.setImageDrawable(getDrawable(R.drawable.ic_user))
        text_view_username.text = getString(R.string.label_NA)
        text_view_name.text = getString(R.string.label_NA)
        text_view_last_name.text = getString(R.string.label_NA)
        text_view_m_last_name.text = getString(R.string.label_NA)
        text_view_enrolment_duration.text = getString(R.string.label_NA)
        text_view_encoding_duration.text = getString(R.string.label_NA)
        text_view_match_person_to_person_duration.text = getString(R.string.label_NA)
        loader = IDMProgress.create(this)
    }
}

/**
 *  UserInfo Display Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface UserInfoDisplayLogic {
    fun displaySearch(viewModel: UserInfoModels.Search.ViewModel)
    fun displayError(viewModel: UserInfoModels.Error.ViewModel)
}