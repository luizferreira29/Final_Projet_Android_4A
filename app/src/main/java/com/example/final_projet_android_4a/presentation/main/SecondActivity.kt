package com.example.final_projet_android_4a.presentation.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_projet_android_4a.R
import com.example.final_projet_android_4a.data.local.models.Beer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_second.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SecondActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private var mbeer = listOf<Beer>()
    private lateinit var adapter: ListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "Pokemon"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        linearLayoutManager = LinearLayoutManager(this)
        list_recycler_view.layoutManager = linearLayoutManager

        var beerList = listOf<Beer>()
        beerList= getDataFromCache()

        if (beerList.isNullOrEmpty()) {
            mbeer = run("https://pokeapi.co/api/v2/pokemon?limit=100&offset=0")
        } else {
            adapter = ListAdapter(beerList)
            runOnUiThread { list_recycler_view.adapter = adapter }
        }
    }

    private fun saveList(beerList: List<Beer>) {
        var gson = Gson()
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonString: String = gson.toJson(beerList)
        sharedPref
            .edit()
            .putString(PREF_NAME, jsonString)
            .apply()
    }

    private fun getDataFromCache(): List<Beer> {
        sharedPref  = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonBeer = sharedPref.getString(PREF_NAME, null)
        var gson = Gson()
        return if (jsonBeer == null) {

            return listOf<Beer>()
        } else {
            val listType =
                object : TypeToken<List<Beer>>() {}.type
            return gson.fromJson(jsonBeer, listType)
        }
    }
    fun run(url: String): List<Beer> {
        var check = 0

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {

                var responses = client.newCall(request).execute();
                val jsonData = responses.body()!!.string()
                val Jobject = JSONObject(jsonData)
                val Jarray = Jobject.getJSONArray("results")

                var tempName : String
                var tempUrl : String
                var mbeer = listOf<Beer>()

                for (i in 0 until Jarray.length()) {
                    tempName = Jarray.getJSONObject(i).getString("name")
                    tempUrl = Jarray.getJSONObject(i).getString("url")
                    mbeer += Beer(tempName,tempUrl)
                }

                adapter = ListAdapter(mbeer)

                println(mbeer.size)
                saveList(mbeer)
                runOnUiThread { list_recycler_view.adapter = adapter }
            }
        })
        return mbeer
    }
}