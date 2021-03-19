@file:Suppress("DEPRECATION")

package com.nitish.kilofarms

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.nitish.kilofarms.adapters.Adapter
import com.nitish.kilofarms.db.Item
import com.nitish.kilofarms.db.ItemDataBase
import kotlinx.android.synthetic.main.activity_home.*
import com.nitish.kilofarms.utils.Constants.Companion.GET_BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var myAdapter: Adapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //Navbar
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.addItems -> {
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

        recyclerView_main.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView_main.layoutManager = linearLayoutManager

        //GettingData
//        getData()
        if(checkNetwork()){
            Log.d("Internet", "Internet")
            getData()
        }
        else if(!checkNetwork()){
            Log.d("Internet", "No Internet")
            GlobalScope.launch(Dispatchers.IO){
                var data = ItemDataBase.getDataBase(this@HomeActivity).itemDao().readAllItem()
                myAdapter = Adapter(baseContext, data)
                myAdapter.notifyDataSetChanged()
                runOnUiThread {
                    recyclerView_main.adapter = myAdapter
                }
            }
        }

    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GET_BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.getData().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                myAdapter = Adapter(baseContext, data.data)
                myAdapter.notifyDataSetChanged()
                runOnUiThread {
                    recyclerView_main.adapter = myAdapter
                }
                GlobalScope.launch ( Dispatchers.IO ){
                    for (item in data.data) {
                        var item = Item(item.skuId, item.skuName)
                        ItemDataBase.getDataBase(this@HomeActivity).itemDao().insertItem(item)
                    }
                }
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkNetwork(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}

