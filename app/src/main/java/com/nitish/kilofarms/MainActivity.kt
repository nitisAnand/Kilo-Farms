package com.nitish.kilofarms

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_login)
        var loginButton = findViewById<Button>(R.id.loginButton)

        val isLoggedIn = loadData()

        if(isLoggedIn == "1")
        {
            var intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            saveData()
            var intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        tv_register.setOnClickListener {
            var intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData(){
        val isLoggedIn = "1"
        val sharedPreferences: SharedPreferences = getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("KEY", isLoggedIn)
        }.apply()
        Toast.makeText(this,"Logged In", Toast.LENGTH_LONG).show()
    }

    private fun loadData(): String? {
        val sharedPreferences: SharedPreferences = getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getString("KEY", "0")
        return isLoggedIn
    }

}
