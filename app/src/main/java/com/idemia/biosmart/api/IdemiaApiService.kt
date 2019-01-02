package com.idemia.biosmart.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.models.UserBiometrics
import com.idemia.biosmart.scenes.user_info.UserInfoModels
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * IDEMIA API Service
 * Endpoints for WS exposed here!
 */
interface IdemiaApiService {

    @POST("api/users/search")
    fun search(@Body request: UserInfoModels.Search.Request): Observable<Response<UserInfoModels.Search.Response>>

    @POST("api/users/enrolment")
    fun enrolment(@Body request: UserBiometrics): Observable<Response<EnrolmentResponse>>

    @POST("api/users/authenticate")
    fun authenticate(@Body request: UserBiometrics)

    @POST("api/users/identify")
    fun identify(@Body request: UserBiometrics)

    // Web Service endpoints
    companion object {
        /**
         * Create Idemia API Service
         */
        fun create(withBaseUrl:String): IdemiaApiService{
            val gson = GsonBuilder()
                .setLenient().create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(withBaseUrl)
                .build()

            return retrofit.create(IdemiaApiService::class.java)
        }
    }
}