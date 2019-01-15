package com.idemia.biosmart.scenes.license

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.utils.AppCache
import kotlinx.android.synthetic.main.activity_license.*
import android.widget.ArrayAdapter
import com.idemia.biosmart.utils.TimeConverter

/**
 *  License Activity
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class LicenseActivity : BaseActivity(), LicenseDisplayLogic {
    private lateinit var interactor: LicenseBusinessLogic    // Interactor
    private lateinit var router: LicenseRoutingLogic         // Router


    companion object {
        private const val TAG = "LicenseActivity"
    }

    //region BASE ACTIVITY
    override fun resourceLayoutId(): Int = R.layout.activity_license
    override fun hideActionBar(): Boolean = false
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity(savedInstanceState: Bundle?) {
        initUi()
        licenseDetails()  // Call use case
    }
    //endregion

    //region VIPER - A "dependency injection"
    override fun inject() {
        val activity = this
        this.interactor = LicenseInteractor()
        this.router = LicenseRouter()
        val presenter = LicensePresenter()
        (this.interactor as LicenseInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as LicenseRouter).setActivity(this)
    }
    //endregion


    //region USECASE - Do something
    private fun licenseDetails() {
        AppCache.license?.let {
            val request = LicenseModels.LicenseDetails.Request(it)
            interactor.licenseDetails(request)
        } ?:run {
            Toast.makeText(applicationContext,"License not found on device", Toast.LENGTH_LONG).show()
        }
    }

    override fun displayLicenseDetails(viewModel: LicenseModels.LicenseDetails.ViewModel) {
        Log.i(TAG, "displayLicenseDetails: ${viewModel.profileId}")
        tv_profile_id.text = viewModel.profileId
        tv_license_id.text = viewModel.licenseId
        tv_status.text = viewModel.status.name

        val features = arrayListOf<String>()

        viewModel.features.forEach { feat ->
            features.add("${feat.name} - ${TimeConverter.timeFromLong(feat.expirationDate.time)}")
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1, features
        )
        lv_features.adapter = adapter
    }
    //endregion

    //region UI - Init Ui
    private fun initUi(){
        tv_status.text = getString(R.string.label_NA)
        tv_license_id.text = getString(R.string.label_NA)
        tv_profile_id.text = getString(R.string.label_NA)
    }
    //endregion
}

/**
 *  License Display Logic
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface LicenseDisplayLogic {
    fun displayLicenseDetails(viewModel: LicenseModels.LicenseDetails.ViewModel)
}