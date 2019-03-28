package com.codewith.kotlinretrofit.api

import com.codewith.kotlinretrofit.model.GetResponse
import com.codewith.kotlinretrofit.model.PostResponse
import com.codewith.kotlinretrofit.pojo.Employee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiServices {

    @POST
    fun empDetails(@Url url:String,@Body employee: Employee): Call<PostResponse>

    @GET
    fun getEmployee(@Url url:String): Call<List<GetResponse>>
}