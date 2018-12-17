package com.idemia.biosmart.api

import com.idemia.biosmart.scenes.welcome.WelcomeModels
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.google.gson.GsonBuilder


/**
 * IDEMIA API Service
 * Endpoints for WS exposed here!
 */
interface IdemiaApiService {

    // Web Service endpoints

    @GET("api/users/")
    fun helloWorld(): Observable<WelcomeModels.HelloWorld.Response>

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