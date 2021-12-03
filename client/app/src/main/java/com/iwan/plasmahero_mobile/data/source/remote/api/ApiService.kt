package com.iwan.plasmahero_mobile.data.source.remote.api

import com.iwan.plasmahero_mobile.data.source.remote.posts.DonorPost
import com.iwan.plasmahero_mobile.data.source.remote.posts.LoginPost
import com.iwan.plasmahero_mobile.data.source.remote.posts.RegisterPost
import com.iwan.plasmahero_mobile.data.source.remote.responses.DonorResponse
import com.iwan.plasmahero_mobile.data.source.remote.responses.LoginResponse
import com.iwan.plasmahero_mobile.data.source.remote.responses.RegisterResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.io.IOException


private const val BASE_URL = "http://192.168.0.102:8000/api/"

interface ApiService {
    @POST("login")
    fun login(
            @Body data: LoginPost
    ): Call<LoginResponse>

    @POST("register")
    fun register(
            @Body data: RegisterPost
    ): Call<RegisterResponse>

    @POST("donors")
    fun createDonor(
            @Body data: DonorPost
    ): Call<DonorResponse>
}

var client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + "26|fmjIne1YaCNz2Suipld6eEHZfwZKmpPa3SbqNtpE")
                .build()
        return chain.proceed(newRequest)
    }
}).build()

object DataService {
    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        return retrofit.create(ApiService::class.java)
    }
}