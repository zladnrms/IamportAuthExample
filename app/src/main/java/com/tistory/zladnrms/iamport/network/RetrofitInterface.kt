package com.papricacare.towndoctor.network

import com.google.gson.GsonBuilder
import com.papricacare.towndoctor.network.data.*
import com.tistory.zladnrms.iamport.BuildConfig
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitInterface {

    companion object {

        fun createForImport(): RetrofitInterface {

            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .addInterceptor { chain -> chain.proceed(chain.request()) }
                .addInterceptor(interceptor)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.IMPORT_SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(RetrofitInterface::class.java)
        }
    }


    /** IAMPORT */
    @GET("/certifications/{imp_uid}")
    suspend fun getAuthProfile(@Header("Authorization") token: String, @Path("imp_uid") imp_uid: String): ProfileResponse

    @FormUrlEncoded
    @POST("/users/getToken")
    suspend fun postAccessToken(@Field("imp_key") imp_key: String, @Field("imp_secret") imp_secret: String): TokenResponse
}