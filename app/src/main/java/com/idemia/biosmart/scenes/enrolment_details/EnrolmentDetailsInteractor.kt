package com.idemia.biosmart.scenes.enrolment_details

import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import io.reactivex.disposables.Disposable

/**
 *  EnrolmentDetails Interactor
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsInteractor : EnrolmentDetailsBusinessLogic {
    private val worker = EnrolmentDetailsWorker()
    private var presenter: EnrolmentDetailsPresentationLogic = EnrolmentDetailsPresenter()
    private var dispoable: Disposable? = null

    companion object {
        val TAG = "EnrolmentDetailsInt"
    }

    fun setPresenter(presenter: EnrolmentDetailsPresentationLogic) {
        this.presenter = presenter
    }

    override fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request) {
        dispoable = worker.enrolPerson(request).subscribe({ responseFromService ->
            val response = EnrolmentDetailsModels.EnrolPerson.Response(responseFromService.body()!!)
            presenter.presentEnrolPerson(response)
        },{ throwable ->
            Log.e(TAG, "enrolPerson:" ,throwable)
        })
        DisposableManager.add(dispoable)
    }
}


/**
 *  EnrolmentDetails Business Logic
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface EnrolmentDetailsBusinessLogic {
    fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request)
}
