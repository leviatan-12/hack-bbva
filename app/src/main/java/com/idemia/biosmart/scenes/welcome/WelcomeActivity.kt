package com.idemia.biosmart.scenes.welcome

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.idemia.biosmart.BioSmartApplication
import com.idemia.biosmart.R
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.scenes.welcome.di.WelcomeModule
import com.idemia.biosmart.scenes.welcome.views.CardsMenuAdapter
import com.idemia.biosmart.utils.IDMProgress
import com.kaopiz.kprogresshud.KProgressHUD
import com.morpho.mph_bio_sdk.android.sdk.common.BioSdkInfo
import kotlinx.android.synthetic.main.activity_welcome.*
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 *  Welcome Activity
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeActivity : BaseActivity(), WelcomeDisplayLogic {
    @Inject lateinit var interactor: WelcomeBusinessLogic    // Interactor
    @Inject lateinit var router: WelcomeRoutingLogic         // Router

    var loader: KProgressHUD? = null

    companion object {
        private val TAG = "WelcomeActivity"
    }

    override fun inject() {
        // TODO: Refactor this code
        val app = application as BioSmartApplication
        app.component.plus(WelcomeModule(this)).inject(this)
        (router as WelcomeRouter).activity = WeakReference(this)
    }

    override fun resourceLayoutId(): Int = R.layout.activity_welcome
    override fun hideActionBar(): Boolean = true
    override fun hideNavigationBar(): Boolean = false

    //region On load activity
    override fun onLoadActivity() {
        setSupportActionBar(bottom_app_bar)
        text_view_license_status.text = getString(R.string.welcome_message_license_not_activated)
        button_settings.setOnClickListener { startProcess(WelcomeModels.Operation.SETTINGS) }
        val sdkInfo = BioSdkInfo(applicationContext)
        text_view_sdk_version.text = sdkInfo.version

        // Linear Layout
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recycle_view_menu.layoutManager = layoutManager
        recycle_view_menu.setHasFixedSize(true)

        val list = arrayListOf(
            WelcomeModels.CardMenu(getString(R.string.label_enrolment),getString(R.string.label_start_process), R.drawable.ic_user_96,"",
                View.OnClickListener {
                    startProcess(WelcomeModels.Operation.ENROLMENT)
                }
            ),
            WelcomeModels.CardMenu(getString(R.string.label_authentication),getString(R.string.label_start_process), R.drawable.ic_apply_96, getString(R.string.authenticate_label_description),
                View.OnClickListener {
                startProcess(WelcomeModels.Operation.AUTHENTICATION)
                }
            ),
            WelcomeModels.CardMenu(getString(R.string.label_identify), getString(R.string.label_start_process), R.drawable.ic_more_info_96, getString(R.string.identify_label_description),
                View.OnClickListener {
                startProcess(WelcomeModels.Operation.IDENTIFY)
                }
            )
        )

        recycle_view_menu.adapter = CardsMenuAdapter(list)

        // Validate or activate license
        activateLkmsLicenseOnDevice()
    }
    //endregion

    override fun onPause() {
        super.onPause()
        destroyComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyComponents()
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
        loader = IDMProgress(this, "Generating License", "Please Wait...").kProgress
        loader?.show()
        val request = WelcomeModels.GenerateLicense.Request()
        interactor.generateLicense(request)
    }

    override fun displayGenerateLicense(viewModel: WelcomeModels.GenerateLicense.ViewModel) {
        loader?.dismiss()
        Log.i(TAG, "displayGenerateLicense: ")
        if(viewModel.generated){
            text_view_license_status.text = getString(R.string.welcome_message_license_bin_file_generated)
            createLKMSLicense(viewModel.activationData!!)
        }else{
            val message = getString(R.string.welcome_message_license_bin_file_not_generated)
            text_view_license_status.text = getString(R.string.welcome_message_license_not_activated, message)
            Toast.makeText(applicationContext, message , Toast.LENGTH_LONG).show()
        }
    }

    //endregion

    //region Create LKMS License on Server
    private fun createLKMSLicense(activationData: ByteArray){
        val lkmsUrlKey = getString(R.string.idemia_key_lkms_url)
        val defaultLkmsUrl = getString(R.string.default_lkms_server_url)
        val lkmsUrlSelected = preferenceManager.getString( lkmsUrlKey , defaultLkmsUrl)
        val request = WelcomeModels.ActivateBinFileLicenseToLkms.Request(activationData, applicationContext, lkmsUrlSelected!!)
        Log.i(TAG, "createLKMSLicense: LKMS Server URL - $lkmsUrlSelected")
        loader = IDMProgress(this, "Activating License on LKMS Server", "Please Wait...").kProgress
        loader?.show()
        interactor.createLKMSLicense(request)
    }

    override fun displayCreateLKMSLicense(viewModel: WelcomeModels.ActivateBinFileLicenseToLkms.ViewModel) {
        loader?.dismiss()
        if(viewModel.activated){
            text_view_license_status.text = getString(R.string.welcome_message_license_activated)
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_activated), Toast.LENGTH_LONG).show()
        }else{
            text_view_license_status.text = getString(R.string.welcome_message_license_not_activated, viewModel.throwable?.message)
            text_view_license_status.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_not_activated, viewModel.throwable?.message), Toast.LENGTH_LONG).show()
        }
    }

    //endregion

    //region Activate Lkms License On Device
    private fun activateLkmsLicenseOnDevice(){
        loader = IDMProgress(this, "Activating License", "Please Wait...").kProgress
        loader?.show()
        val request = WelcomeModels.ActivateLkmsLicenseOnDevice.Request(applicationContext)
        interactor.activateLkmsLicenseOnDevice(request)
    }

    override fun displayActivateLkmsLicenseOnDevice(viewModel: WelcomeModels.ActivateLkmsLicenseOnDevice.ViewModel) {
        loader?.dismiss()
        if(viewModel.isLicenseValid){
            text_view_license_status.setTextColor(resources.getColor(android.R.color.holo_green_light))
            text_view_license_status.text = getString(R.string.welcome_message_license_activated)
        }else {
            Log.i(TAG, "License is not active or is not valid, a new one will be generated...")
            generateLicense()
            text_view_license_status.setTextColor(resources.getColor(android.R.color.holo_red_dark))
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

    private fun destroyComponents(){
        DisposableManager.dispose()     // Dispose all subscriptions
        loader?.dismiss()
        loader = null                   // Destroy loader
    }
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