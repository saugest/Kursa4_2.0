package com.example.adex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adex.data.UserApi
import com.example.adex.databinding.ActivityIncomingProposalBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IncomingProposalActivity: AppCompatActivity() {
    private lateinit var binding: ActivityIncomingProposalBinding
    private lateinit var adapter: IncomingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomingProposalBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_incoming_proposal)
        setContentView(binding.root)
        adapter = IncomingAdapter()
        binding.rcIncoming.layoutManager = LinearLayoutManager(this)
        binding.rcIncoming.adapter = adapter
        val currentUserId = intent.getIntExtra("currentUserId", 0)
        val currentUserName = findViewById<TextView>(R.id.userName)
        currentUserName.text =  intent.getStringExtra("currentUserName")
        val currentUserLogin = findViewById<TextView>(R.id.textLogin)
        currentUserLogin.text = intent.getStringExtra("currentUserLogin")
        val btLapeActivity = findViewById<Button>(R.id.btLapeActivity)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)

        btLapeActivity.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@IncomingProposalActivity, LapeActivity::class.java)
                    intent.putExtra("currentUserName", currentUserName.text)
                    intent.putExtra("currentUserId", currentUserId)
                    intent.putExtra("currentUserLogin", currentUserLogin.text)
                    startActivity(intent)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val list = mainApi.getOfferRecipient(currentUserId)
            runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }
    }
}