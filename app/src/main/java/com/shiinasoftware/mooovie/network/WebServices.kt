package com.shiinasoftware.mooovie.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shiinasoftware.mooovie.BuildConfig
import com.shiinasoftware.mooovie.network.BaseURL.API_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object BaseURL {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = BuildConfig.API_KEY
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p"
    const val SMALL_IMAGE_URL = "${BASE_IMAGE_URL}/w185"
    const val ORIGINAL_IMAGE_URL = "${BASE_IMAGE_URL}/original"
}

private val headerInterceptor = object : Interceptor {
    // Interceptor for adding Header
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalHttpUrl: HttpUrl = request.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val requestBuilder: Request.Builder = request.newBuilder()
            .url(url)
        return chain.proceed(requestBuilder.build())
    }
}


private fun logging(): HttpLoggingInterceptor {
    // Interceptor for adding Logging (DEBUGGER)
    // Disabled when production
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.HEADERS
    return logging
}

private fun getInterceptor(): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(logging()).addInterceptor(headerInterceptor).build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BaseURL.BASE_URL)
    .client(getInterceptor())
    .build()

val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }



