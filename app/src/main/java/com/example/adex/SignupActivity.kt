package com.example.adex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.adex.data.UserApi
import com.example.adex.data.UserSignup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val b = findViewById<Button>(R.id.signupButton)

        val login = findViewById<EditText>(R.id.signupLogin).text
        val password = findViewById<EditText>(R.id.signupPassword).text
        val firstName = findViewById<EditText>(R.id.signupFirstName).text
        val secondName = findViewById<EditText>(R.id.signupSecondName).text
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userApi = retrofit.create(UserApi::class.java)

        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val req = UserSignup(login.toString(), password.toString(), firstName.toString(), secondName.toString())
                userApi.createUser(req)
                runOnUiThread {
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}