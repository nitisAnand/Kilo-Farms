package com.nitish.kilofarms

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.gson.internal.bind.ArrayTypeAdapter
import com.nitish.kilofarms.adapters.Adapter
import com.nitish.kilofarms.utils.Constants
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class AddItem : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var unit:String
    lateinit var cat:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.addItems ->{
                    var intent = Intent(applicationContext, AddItem::class.java)
                    startActivity(intent)
                }
                R.id.showItems -> {
                    var intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.logOut -> {
                    val isLoggedIn = "0"
                    val sharedPreferences: SharedPreferences = getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.apply{
                        putString("KEY", isLoggedIn)
                    }.apply()
                    Toast.makeText(this,"Logged Out", Toast.LENGTH_LONG).show()
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        skuUnitSpi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                unit = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        skuCatSpi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                cat = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        postButton.setOnClickListener {

            var name = skuName.text.toString()
            if (name.isEmpty()) {
                skuName.error = "Could not be empty"
                skuName.requestFocus()
                return@setOnClickListener
            }

            val dataSent = DataSend(
                skuName = name,
                userId = "1",
                skuUnit = unit,
                skuCategory = cat
            )

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.POST_BASE_URL)
                .build()
                .create(ApiInterface::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                retrofitBuilder.postData(dataSent).enqueue(object : Callback<DataSend?> {
                    override fun onResponse(call: Call<DataSend?>, response: Response<DataSend?>) {
                        Log.d("Main", name)
                        Log.d("Main", unit)
                        Log.d("Main", cat)
                        Log.d("Main", response.body().toString())
                        Toast.makeText(applicationContext,"Item Added",Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<DataSend?>, t: Throwable) {
                        Log.d("Main", "error")
                        Toast.makeText(applicationContext,"Check your internet connection",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
