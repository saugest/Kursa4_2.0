package com.example.adex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adex.data.OfferCreated
import com.example.adex.data.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProposalActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proposal)
        val titleApplicationConsumer = findViewById<TextView>(R.id.offerTitle)
        titleApplicationConsumer.text = intent.getStringExtra("titleApplication")
        val userName = findViewById<TextView>(R.id.userName)
        userName.text = intent.getStringExtra("currentUserName")
        val buttonAccountLape = findViewById<TextView>(R.id.buttonLapeActivity)
        val currentUserLogin = intent.getStringExtra("currentUserLogin")
        val currentUserId = intent.getIntExtra("currentUserId", 0)
        val applicationId = intent.getIntExtra("applicationId", 0)
        val textOffer = findViewById<EditText>(R.id.textOffer).text

        buttonAccountLape.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@ProposalActivity, LapeActivity::class.java)
                    intent.putExtra("currentUserName", userName.text)
                    intent.putExtra("currentUserId", currentUserId)
                    intent.putExtra("currentUserLogin", currentUserLogin)
                    startActivity(intent)
                }
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.progadex.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(UserApi::class.java)

        val buttonSend = findViewById<Button>(R.id.buttonSend)
        buttonSend.setOnClickListener {
            val offer = OfferCreated(currentUserId, applicationId, textOffer.toString());
            CoroutineScope(Dispatchers.IO).launch {
                mainApi.createOffer(offer)
                runOnUiThread {
                    val intent = Intent(this@ProposalActivity, OfferActivity::class.java)
                    intent.putExtra("currentUserName", userName.text)
                    intent.putExtra("currentUserId", currentUserId)
                    intent.putExtra("currentUserLogin", currentUserLogin)
                    startActivity(intent)
                }
            }
        }
    }
}