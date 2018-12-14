package com.idemia.biosmart.scenes.welcome

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.idemia.biosmart.R
import com.idemia.biosmart.base.BaseActivity
import com.idemia.biosmart.components.MenuCardView
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
        setupActionBar()
    }

    //region Action Bar / Menu
    private fun setupActionBar(){
        setSupportActionBar(bottom_app_bar)
        menu_card_view_enrolment.buttonAction.setOnClickListener {
            startProcess()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menu_item_generate_license -> {
                generateLicense()
                // Just for testing
                router.routeToEnrolmentScene()
            }
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
            Toast.makeText(applicationContext, getString(R.string.message_license_activated), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext, getString(R.string.message_license_not_generated), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Go to next view
     */
    private fun startProcess(){
        val request = WelcomeModels.StartEnrollment.Request()
        interactor.startEnrollment(request)
    }

    override fun displayStartEnrolment(viewModel: WelcomeModels.StartEnrollment.ViewModel) {
        router.routeToEnrolmentScene()
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
    fun displayStartEnrolment(viewModel: WelcomeModels.StartEnrollment.ViewModel)
}