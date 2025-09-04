package com.dalima.wikipedia_codenicely_assignment

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val logging= HttpLoggingInterceptor().apply{
        level=HttpLoggingInterceptor.Level.BASIC
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "WikiReaderApp/1.0 (dalima62657@gmail.com)")
                .build()
            chain.proceed(request)
        }
        .build()


    val wikiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }
    val commonsRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://commons.wikimedia.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val commonsService: ApiService by lazy {
        commonsRetrofit.create(ApiService::class.java)
    }
}