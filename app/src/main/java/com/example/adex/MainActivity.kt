package com.example.adex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adex.data.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val b = findViewById<Button>(R.id.button)
        val b2 = findViewById<Button>(R.id.button2)

        val enterLogin = findViewById<EditText>(R.id.enterLogin).text
        val enterPassword = findViewById<EditText>(R.id.enterPassword).text
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)

        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = mainApi.getUserById(enterLogin.toString(), enterPassword.toString())
                Log.v("QWER", user.id.toString())

                Log.v("QWER", "QWER")
                runOnUiThread {
                    val intent = Intent(this@MainActivity, LapeActivity::class.java)

                    intent.putExtra("currentUserName", user.name + " " + user.surname)
                    intent.putExtra("currentUserId", user.id)
                    intent.putExtra("currentUserLogin", user.login)
                    startActivity(intent)
                }
            }
        }

        b2.setOnClickListener {
            val intent = Intent(this@MainActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}