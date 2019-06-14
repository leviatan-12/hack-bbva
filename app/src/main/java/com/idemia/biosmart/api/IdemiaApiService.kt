package com.idemia.biosmart.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.idemia.biosmart.models.*
import com.idemia.biosmart.scenes.user_info.UserInfoModels
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * IDEMIA API Service
 * Endpoints for WS exposed here!
 */
interface IdemiaApiService {

    //region IDEMIA API SERVICE OBJ
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
    //endregion

    //region Web Service endpoints
    @POST("api/users/search")
    fun search(@Body request: UserInfoModels.SearchPersonRequest): Observable<Response<UserInfoModels.Search.Response>>

    @POST("api/users/enrolment")
    fun enrolment(@Body request: UserBiometrics): Observable<Response<EnrolmentResponse>>

    @POST("api/users/authenticate")
    fun authenticate(@Body request: UserBiometrics): Observable<Response<AuthenticationResponse>>

    @POST("api/users/identify")
    fun identify(@Body request: UserBiometrics): Observable<Response<IdentifyResponse>>

    @POST("api/users/create")
    fun create(@Body request: UserData): Observable<Response<CreatePersonResponse>>
    //endregion
}