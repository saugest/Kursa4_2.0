package com.example.adex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adex.data.UserApi
import com.example.adex.databinding.ActivityOfferBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OfferActivity: AppCompatActivity() {

    private lateinit var adapter: OfferAdapter
    private lateinit var binding: ActivityOfferBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_offer)
        setContentView(binding.root)
        adapter = OfferAdapter()
        binding.rcOffer.layoutManager = LinearLayoutManager(this)
        binding.rcOffer.adapter = adapter
        val currentUserName = findViewById<TextView>(R.id.userName)
        currentUserName.text =  intent.getStringExtra("currentUserName")
        val userId = intent.getIntExtra("currentUserId", 0)
        val login = intent.getStringExtra("currentUserLogin")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val list = mainApi.getOfferConsumer(userId)
            runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }
        val buttonActivityLape = findViewById<Button>(R.id.buttonActivityLape)
        buttonActivityLape.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@OfferActivity, LapeActivity::class.java)
                    intent.putExtra("currentUserName", currentUserName.text)
                    intent.putExtra("currentUserId", userId)
                    intent.putExtra("currentUserLogin", login)
                    startActivity(intent)
                }
            }
        }
    }
}