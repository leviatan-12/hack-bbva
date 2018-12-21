package com.idemia.biosmart.scenes.welcome

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity
import com.morpho.mph_bio_sdk.android.sdk.BioSdk
import com.morpho.mph_bio_sdk.android.sdk.common.BioSdkInfo
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 *  Welcome Activity
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeActivity : BaseActivity(), WelcomeDisplayLogic {
    private lateinit var interactor: WelcomeBusinessLogic    // Interactor
    private lateinit var router: WelcomeRoutingLogic         // Router

    companion object {
        private val TAG = "WelcomeActivity"
    }

    override fun inject() {
        val activity = this
        this.interactor = WelcomeInteractor()
        this.router = WelcomeRouter()
        val presenter = WelcomePresenter()
        (this.interactor as WelcomeInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as WelcomeRouter).setActivity(this)
    }

    override fun resourceLayoutId(): Int = R.layout.activity_welcome
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = false

    override fun onLoadActivity() {
        setSupportActionBar(bottom_app_bar)
        text_view_license_status.text = getString(R.string.welcome_message_license_not_activated)
        menu_card_view_enrolment.buttonAction.setOnClickListener { startProcess(WelcomeModels.Operation.ENROLMENT) }
        menu_card_view_authenticate.buttonAction.setOnClickListener{ startProcess(WelcomeModels.Operation.AUTHENTICATION) }
        menu_card_view_identify.buttonAction.setOnClickListener { startProcess(WelcomeModels.Operation.IDENTIFY) }
        button_settings.setOnClickListener { startProcess(WelcomeModels.Operation.SETTINGS) }
        val sdkInfo = BioSdkInfo(applicationContext)
        text_view_sdk_version.text = sdkInfo.version

        // Validate or activate license
        activateLkmsLicenseOnDevice()
    }

    //region Action Bar / Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menu_item_generate_license -> generateLicense()
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Generate License Usecase
    /**
     * Generate License Use Case
     */
    private fun generateLicense() {
        val request = WelcomeModels.GenerateLicense.Request()
        interactor.generateLicense(request)
    }

    override fun displayGenerateLicense(viewModel: WelcomeModels.GenerateLicense.ViewModel) {
        Log.i(TAG, "displayGenerateLicense: ")
        if(viewModel.generated){
            text_view_license_status.text = getString(R.string.welcome_message_license_bin_file_generated)
            createLKMSLicense(viewModel.activationData!!)
        }else{
            text_view_license_status.text = getString(R.string.welcome_message_license_not_activated)
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_bin_file_not_generated), Toast.LENGTH_LONG).show()
        }
    }

    //endregion

    //region Create LKMS License
    fun createLKMSLicense(activationData: ByteArray){
        val request = WelcomeModels.ActivateBinFileLicenseToLkms.Request(activationData,
            applicationContext,
            "https://service-intg.dictao.com/lkms-server-app")
        interactor.createLKMSLicense(request)
    }

    override fun displayCreateLKMSLicense(viewModel: WelcomeModels.ActivateBinFileLicenseToLkms.ViewModel) {
        if(viewModel.activated){
            text_view_license_status.text = getString(R.string.welcome_message_license_activated)
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_activated), Toast.LENGTH_LONG).show()
        }else{
            text_view_license_status.text = getString(R.string.welcome_message_license_not_activated)
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_not_activated), Toast.LENGTH_LONG).show()
        }
    }

    //endregion

    //region Activate Lkms License On Device
    private fun activateLkmsLicenseOnDevice(){
        val request = WelcomeModels.ActivateLkmsLicenseOnDevice.Request(applicationContext)
        interactor.activateLkmsLicenseOnDevice(request)
    }

    override fun displayActivateLkmsLicenseOnDevice(viewModel: WelcomeModels.ActivateLkmsLicenseOnDevice.ViewModel) {
        if(viewModel.isLicenseValid){
            text_view_license_status.text = getString(R.string.welcome_message_license_activated)
        }else {
            Log.i(TAG, "License is not active or is not valid, a new one will be generated...")
            generateLicense()
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_is_not_active), Toast.LENGTH_LONG).show()
        }
    }
    //endregion

    //region Start Process
    /**
     * Go to next view
     */
    private fun startProcess(operation: WelcomeModels.Operation){
        val request = WelcomeModels.StartEnrollment.Request(operation)
        interactor.startProcess(request)
    }

    override fun displayStartProcess(viewModel: WelcomeModels.StartEnrollment.ViewModel) {
        when(viewModel.operation){
            WelcomeModels.Operation.ENROLMENT -> router.routeToEnrolmentScene()
            WelcomeModels.Operation.AUTHENTICATION -> router.routeToAuthenticationScene()
            WelcomeModels.Operation.IDENTIFY -> router.routeToIdentifyScene()
            WelcomeModels.Operation.SETTINGS -> router.routeToSettingsScene()
        }
    }
    //endregion
}

/**
 *  Welcome Display Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface WelcomeDisplayLogic {
    fun displayGenerateLicense(viewModel: WelcomeModels.GenerateLicense.ViewModel)
    fun displayCreateLKMSLicense(viewModel: WelcomeModels.ActivateBinFileLicenseToLkms.ViewModel)
    fun displayActivateLkmsLicenseOnDevice(viewModel: WelcomeModels.ActivateLkmsLicenseOnDevice.ViewModel)
    fun displayStartProcess(viewModel: WelcomeModels.StartEnrollment.ViewModel)
}