package com.ubaya.student.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.student.model.Student

class ListViewModel(app: Application): AndroidViewModel(app) {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "student data fetch"
    private var queue: RequestQueue? = null

    override fun onCleared() {
        super.onCleared()
        //Untuk cancel volley
        queue?.cancelAll(TAG)
    }

    fun refresh() {
        loadingLD.value = true
        studentLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"

        //metode, url, sukses, error
        val sr = StringRequest(
            Request.Method.GET,
            url,
            {
                //success
                loadingLD.value = false

                //read GSON
                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value  = result as ArrayList<Student>?
                loadingLD.value = false
                Log.d("showvoley", result.toString())
            },
            {
                //error
                studentLoadErrorLD.value = true
                loadingLD.value = false
                Log.d("showvoley", it.toString())
            }
        )
        sr.tag = TAG
        queue?.add(sr)


    }
}
