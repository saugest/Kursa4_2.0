package com.example.adex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor)
        val nameAuthor = findViewById<TextView>(R.id.userName)
        nameAuthor.text =  intent.getStringExtra("currentUserName")
        val buttonMainPage = findViewById<Button>(R.id.buttonMainPage)
        val userId = intent.getIntExtra("currentUserId", 0)
        val login = intent.getStringExtra("currentUserLogin")

        buttonMainPage.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    val intent = Intent(this@AutorActivity, LapeActivity::class.java)
                    intent.putExtra("currentUserName", nameAuthor.text)
                    intent.putExtra("currentUserId", userId)
                    intent.putExtra("currentUserLogin", login)
                    startActivity(intent)
                }
            }
        }
    }
}