package com.idemia.biosmart.scenes.user_info

import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity
import com.idemia.biosmart.utils.Base64
import kotlinx.android.synthetic.main.activity_userinfo.*
import android.graphics.BitmapFactory
import com.idemia.biosmart.base.DisposableManager
import com.idemia.biosmart.scenes.user_info.view.adapters.ViewPageUserInfoAdapter
import com.idemia.biosmart.scenes.user_info.view.fragments.UserInfoDataFragment
import com.idemia.biosmart.scenes.user_info.view.fragments.UserInfoTechnicalDetailsFragment
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

    private val userInfoDataFragment = UserInfoDataFragment()
    private val userInfoTechnicalDetailsFragment = UserInfoTechnicalDetailsFragment()
    lateinit var loader: KProgressHUD

    override fun onLoadActivity() {
        initViewPager()
        // TODO: Call this to search user after authenticate or identify user
        search("alfredo")
    }

    override fun onDestroy() {
        super.onDestroy()
        DisposableManager.dispose()
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
        loader = IDMProgress(this, "Getting User Info", "Please Wait...").kProgress
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
        user.photo?.let {
            val photo = Base64.decode(user.photo)
            val options = BitmapFactory.Options()
            options.inMutable = true
            val bmp = BitmapFactory.decodeByteArray(photo, 0, photo.size, options)
            image_view_photo.setImageBitmap(bmp)
        }
        userInfoDataFragment.dataBinding(user)
    }

    private fun displaySearchNotFound(message: String){
        Toast.makeText(applicationContext, message.toLowerCase().capitalize(), Toast.LENGTH_LONG).show()
        userInfoDataFragment.dataBinding(null)
        loader.dismiss()
    }
    //endregion

    //region Display Error
    override fun displayError(viewModel: UserInfoModels.Error.ViewModel) {
        Toast.makeText(applicationContext, viewModel.throwable.localizedMessage, Toast.LENGTH_LONG).show()
        loader.dismiss()
    }
    //endregion

    private fun initViewPager(){
        val adapter  = ViewPageUserInfoAdapter(supportFragmentManager)
        adapter.addFragment(userInfoDataFragment, "User Info")
        adapter.addFragment(userInfoTechnicalDetailsFragment, "Technical Details")
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
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