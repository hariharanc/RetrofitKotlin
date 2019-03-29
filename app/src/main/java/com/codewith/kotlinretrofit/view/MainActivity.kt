package com.codewith.kotlinretrofit.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codewith.kotlinretrofit.R
import com.codewith.kotlinretrofit.model.GetResponse
import com.codewith.kotlinretrofit.model.PostResponse
import com.codewith.kotlinretrofit.pojo.Employee
import com.codewith.kotlinretrofit.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Observer, View.OnClickListener {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = MainViewModel()
        mainViewModel.addObserver(this)
        btnPost.setOnClickListener(this)
        btnGet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPost -> {
                val employee = Employee()
                employee.age = "123"
                employee.name = "test" + UUID.randomUUID().toString()
                employee.salary = "23"
                mainViewModel.postEmployeeDetails(employee)
            }
            R.id.btnGet -> {
                mainViewModel.getEmployees()
            }
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        when (mainViewModel.getApiType()) {
            "POST" -> {
                if (arg is PostResponse) {
                    Log.i("MainActivity", "API response Name:" + arg.name)
                }
            }
            "GET" -> {
                if (arg is List<*>) {
                    val empListData: List<GetResponse> = arg.filterIsInstance<GetResponse>()
                    Log.i("MainActivity", "API response Name:" + empListData.size)
                    for (item in empListData) {
                        Log.i("MainActivity", "API response Name:" + item.employee_name)
                        Log.i("MainActivity", "API response Age:" + item.employee_age)
                    }
                }
            }
        }
    }


}
