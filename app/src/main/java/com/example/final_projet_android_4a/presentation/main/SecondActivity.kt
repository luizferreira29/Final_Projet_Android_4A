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
    private val PREF_NAME = "myApp"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        linearLayoutManager = LinearLayoutManager(this)
        list_recycler_view.layoutManager = linearLayoutManager


        //showList();
        var beerList = listOf<Beer>()
        beerList= getDataFromCache()


        if (beerList.isNullOrEmpty()) {
            //Toast.makeText(getApplicationContext(), "Liste existe", Toast.LENGTH_SHORT).show();
            mbeer = run("https://pokeapi.co/api/v2/pokemon?limit=100&offset=200")


        } else {
            // Toast.makeText(getApplicationContext(), "Liste existe pas", Toast.LENGTH_SHORT).show();
            adapter = ListAdapter(beerList)



            runOnUiThread { list_recycler_view.adapter = adapter }
        }

    }
    private fun saveList(dbzList: List<Beer>) {
        var gson = Gson()
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonString: String = gson.toJson(dbzList)
        sharedPref
            .edit()
            .putString(PREF_NAME, jsonString)
            .apply()
        //Toast.makeText(getApplicationContext(), "Liste sauvegarde", Toast.LENGTH_SHORT).show();
    }
    private fun getDataFromCache(): List<Beer> {
        sharedPref  = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonDbz = sharedPref.getString(PREF_NAME, null)
        var gson = Gson()
        return if (jsonDbz == null) {

            return listOf<Beer>()
        } else {
            val listType =
                object : TypeToken<List<Beer>>() {}.type
            return gson.fromJson(jsonDbz, listType)
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
                var tempSpecie : String
                var mbeer = listOf<Beer>()
                for (i in 0 until Jarray.length()) {
                    tempName = Jarray.getJSONObject(i).getString("name")
                    tempSpecie = Jarray.getJSONObject(i).getString("url")
                    mbeer += Beer(tempName,tempSpecie)
                }

                adapter = ListAdapter(mbeer)

                println(mbeer.size)
                saveList(mbeer)
                runOnUiThread { list_recycler_view.adapter = adapter }

                // Stuff that updates the UI
            }
        })
        return mbeer
    }
}