package com.dalima.wikipedia_codenicely_assignment

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val req = chain.request().newBuilder()
                // Wikimedia asks for descriptive User-Agent: app/version (contact)
                .header("User-Agent", "WikiReaderApp/1.0 (dalima62657@gmail.com)")
                .build()
            chain.proceed(req)
        }
        .build()

    val wikiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val commonsService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://commons.wikimedia.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
