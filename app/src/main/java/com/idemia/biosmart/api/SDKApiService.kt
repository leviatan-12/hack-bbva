package com.idemia.biosmart.api

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface SDKApiService {

    @GET("LicenseRequest")
    @Headers("Accept: application/octet-stream","Content-Type:application/octet-stream")
    fun generateLicenseBinFile(): Observable<ResponseBody>

    // Web Service endpoints
    companion object {
        /*** Create SDK API Service*/
        fun create(withBaseUrl:String): SDKApiService{
            val gson = GsonBuilder()
                .setLenient().create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(withBaseUrl)
                .build()

            return retrofit.create(SDKApiService::class.java)
        }
    }
}