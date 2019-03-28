package com.codewith.kotlinretrofit.pojo

import com.google.gson.annotations.SerializedName

class Employee {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("salary")
    var salary: String? = null
    @SerializedName("age")
    var age: String? = null
}