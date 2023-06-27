package com.example.adex

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adex.data.UserApi
import com.example.adex.databinding.ActivityLapeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LapeActivity : AppCompatActivity() {
    private lateinit var adapter: ApplicationAdapter
    private lateinit var binding: ActivityLapeBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val menuq = arrayOf("Реклама под ключ", "Входящие заявки", "Исходящие заявки")
        super.onCreate(savedInstanceState)
        binding = ActivityLapeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lape)
        setContentView(binding.root)
        adapter = ApplicationAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)

        val userId = intent.getIntExtra("currentUserId", 0)
        val login = intent.getStringExtra("currentUserLogin")
        val titleApplicationRecipient = intent.getStringExtra("titleApplicationRecipient")

        val nameLape = findViewById<TextView>(R.id.userName)
        nameLape.text = intent.getStringExtra("currentUserName")
        val bt = findViewById<Button>(R.id.buttonTelegram)
        val bvk = findViewById<Button>(R.id.buttonVK)
        val avatar = findViewById<ImageButton>(R.id.buttonAvatar)
        val spinnerLape = findViewById<Spinner>(R.id.spinnerLape)
        val arrayAdapter = ArrayAdapter<String>(this@LapeActivity, android.R.layout.simple_spinner_dropdown_item, menuq)
        spinnerLape.adapter = arrayAdapter
        spinnerLape.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        val intent = Intent(this@LapeActivity, IncomingProposalActivity::class.java)
                        intent.putExtra("currentUserName", nameLape.text)
                        intent.putExtra("currentUserId", userId)
                        intent.putExtra("currentUserLogin", login)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@LapeActivity, OfferActivity::class.java)
                        intent.putExtra("currentUserName", nameLape.text)
                        intent.putExtra("currentUserId", userId)
                        intent.putExtra("currentUserLogin", login)
                        startActivity(intent)
                    }
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        avatar.setOnClickListener{
            val intent = Intent(this@LapeActivity, PersonalAccountActivity::class.java)
            intent.putExtra("currentUserName", nameLape.text)
            intent.putExtra("currentUserId", userId)
            intent.putExtra("currentUserLogin", login)
            startActivity(intent)
        }

        bt.setOnClickListener{
            bt.setBackgroundColor(Color.parseColor("#6281AC"))
            bvk.setBackgroundColor(Color.parseColor("#B5CEE1"))
            CoroutineScope(Dispatchers.IO).launch {
                val list = mainApi.getApplicationsTG()
                runOnUiThread {
                    binding.apply {
                        adapter.submitList(list)
                    }
                }
            }
        }

        bvk.setOnClickListener{
            bvk.setBackgroundColor(Color.parseColor("#6281AC"))
            bt.setBackgroundColor(Color.parseColor("#B5CEE1"))
            CoroutineScope(Dispatchers.IO).launch {
                val list = mainApi.getApplicationsVK()
                runOnUiThread {
                    binding.apply {
                        adapter.submitList(list)
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val list = mainApi.getAllApplications().map {
                it.copy(currentUserName = nameLape.text.toString(), currentUserId = userId, currentUserLogin = login.toString(), titleApplicationRecipient = titleApplicationRecipient.toString())
            }
            runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }
    }
}