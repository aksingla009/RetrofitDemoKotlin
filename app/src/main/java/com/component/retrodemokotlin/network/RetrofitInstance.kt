package com.component.retrodemokotlin.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object{

        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        //ConnectTimeout is the time period in which our app should establish connection with server
        //ReadTimeout is maximum time gap between arrival of 2 data packets when waitig for server's response
        //WriteTimeout is maximum time gap between 2 data packets when sending them to the server

        //These timeouts should be decided based on internet speed, performance of server and performance level of app
        val okHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(25,TimeUnit.SECONDS)
        }.build()


        val BASE_URL =  "https://jsonplaceholder.typicode.com/"
        fun getRetrofitInstance(): Retrofit{

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}