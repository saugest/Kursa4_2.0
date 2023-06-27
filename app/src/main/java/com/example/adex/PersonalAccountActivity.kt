package com.example.adex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adex.data.UserApi
import com.example.adex.databinding.ActivityPersonalAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonalAccountActivity : AppCompatActivity() {
    private lateinit var adapter: ApplicationAdapter
    private lateinit var binding: ActivityPersonalAccountBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalAccountBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_personal_account)
        setContentView(binding.root)
        adapter = ApplicationAdapter()
        binding.rcView2.layoutManager = LinearLayoutManager(this)
        binding.rcView2.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)
        val titleApplicationRecipient = intent.getStringExtra("titleApplicationRecipient")
        val buttonAuthor = findViewById<Button>(R.id.buttonAuthor)
        val buttonLogOut = findViewById<Button>(R.id.buttonLogOut)
        val namePersonalAccount = findViewById<TextView>(R.id.userName)
        val buttonAccountLape = findViewById<Button>(R.id.buttonAccountLape)
        namePersonalAccount.text =  intent.getStringExtra("currentUserName")

        val userId = intent.getIntExtra("currentUserId", 0)
        val login = intent.getStringExtra("currentUserLogin")
        val textLogin = findViewById<TextView>(R.id.textLogin)
        textLogin.text = "Логин: " + login

        buttonAuthor.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@PersonalAccountActivity, AutorActivity::class.java)
                    intent.putExtra("currentUserName", namePersonalAccount.text)
                    intent.putExtra("currentUserId", userId)
                    intent.putExtra("currentUserLogin", login)
                    startActivity(intent)
                }
            }
        }
        buttonLogOut.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@PersonalAccountActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        buttonAccountLape.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@PersonalAccountActivity, LapeActivity::class.java)
                    intent.putExtra("currentUserName", namePersonalAccount.text)
                    intent.putExtra("currentUserId", userId)
                    intent.putExtra("currentUserLogin", login)
                    startActivity(intent)
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            val list = mainApi.getApplicationsForUser(intent.getIntExtra("currentUserId", 0))
            runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }
    }
}