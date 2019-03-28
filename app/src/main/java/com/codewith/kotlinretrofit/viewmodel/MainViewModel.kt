package com.codewith.kotlinretrofit.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import com.codewith.kotlinretrofit.api.ApiServices
import com.codewith.kotlinretrofit.listener.ApiResponseListener
import com.codewith.kotlinretrofit.model.GetResponse
import com.codewith.kotlinretrofit.model.PostResponse
import com.codewith.kotlinretrofit.pojo.Employee
import com.codewith.kotlinretrofit.util.ApiUtils
import com.codewith.kotlinretrofit.util.RetrofitClient
import com.codewith.kotlinretrofit.util.UrlRequest
import retrofit2.Call
import java.util.*

class MainViewModel :Observable(),ApiResponseListener {
    private var retrofitClient: RetrofitClient
    private var apiService: ApiServices
    val observablePostResponse = ObservableField<Any>()
    init {
        retrofitClient = RetrofitClient()
        apiService = retrofitClient.getRetrofitApiService(ApiUtils.BASE_URL)
    }

    fun postEmployeeDetails(employee: Employee) {
        val postRes: Call<PostResponse> = apiService.empDetails(UrlRequest.EMPDETAILS_URL, employee)
        retrofitClient.addRetrofitCalls(postRes, this, UrlRequest.EMPDETAILS_URL)
    }

    fun getEmployees() {
        val postRes: Call<List<GetResponse>> = apiService.getEmployee(UrlRequest.EMPDETAILS_GET)
        retrofitClient.addRetrofitCalls(postRes, this, UrlRequest.EMPDETAILS_GET)
    }

    override fun onReceiveResult(request: String, response: retrofit2.Response<*>) {
        when (request) {
            UrlRequest.EMPDETAILS_URL -> {
                Log.i("MainActivity", "API response:" + response.isSuccessful)
                val postResponse: PostResponse = response.body() as PostResponse
                observablePostResponse.set(postResponse)
                setChanged()
                notifyObservers(postResponse)
            }
            UrlRequest.EMPDETAILS_GET -> {
                val empListData: List<GetResponse>? = response.body() as List<GetResponse>
                observablePostResponse.set(empListData)
                setChanged()
                notifyObservers(empListData)
            }
            else -> Log.i("MainActivity", "API condition Failes")
        }
    }

    override fun onReceiveError(request: String, error: String) {
        Log.i("MainActivity", "API Error:" + error)
    }
}