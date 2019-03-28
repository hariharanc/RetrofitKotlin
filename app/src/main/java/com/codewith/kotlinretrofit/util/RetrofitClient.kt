package com.codewith.kotlinretrofit.util

import android.util.Log
import com.codewith.kotlinretrofit.api.ApiServices
import com.codewith.kotlinretrofit.listener.ApiResponseListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    lateinit var apiServices: ApiServices

    fun getRetrofitApiService(baseUrl: String): ApiServices {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                //header
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build()
                return@Interceptor chain.proceed(request)
            })
            .addInterceptor(interceptor)
            //  .connectTimeout(100, TimeUnit.SECONDS)
            // .readTimeout(100, TimeUnit.SECONDS)
            .build()
        apiServices = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServices::class.java)

        return apiServices
    }

    fun <T> addRetrofitCalls(
        baseCall: retrofit2.Call<T>,
        apiResponseListener: ApiResponseListener,
        request: String
    ) {
        baseCall.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                if (response?.body() != null) {
                    if (response.code() == 200) {
                        apiResponseListener.onReceiveResult(request, response)
                    }
                }
            }
        })
    }


}