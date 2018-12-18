package com.idemia.biosmart.scenes.welcome

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity
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

    override fun inject() {
        val activity = this
        this.interactor = WelcomeInteractor()
        this.router = WelcomeRouter()
        val presenter = WelcomePresenter()
        (this.interactor as WelcomeInteractor).setPresenter(presenter)
        presenter.setActivity(activity)
        (router as WelcomeRouter).setActivity(this)
    }

    companion object {
        private val TAG = "WelcomeActivity"
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
        text_view_sdk_version.text = "v4.13.0"
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

    //region Use Cases
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
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_activated), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext, getString(R.string.welcome_message_license_not_generated), Toast.LENGTH_LONG).show()
        }
    }

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
    fun displayStartProcess(viewModel: WelcomeModels.StartEnrollment.ViewModel)
}