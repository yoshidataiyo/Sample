package com.example.myapplication



import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ZandakaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zandaka)

        val pref = getSharedPreferences("file_wallet", Context.MODE_PRIVATE)
        val storedText = pref.getString("key", "0")
        val editText = findViewById<EditText>(R.id.editText)
        editText.setText(storedText)
        val button = findViewById<Button>(R.id.store)
        button.setOnClickListener {
            val inputText = editText.text.toString()
            pref.edit().putString("key", inputText).apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("inputText", inputText)


            startActivity(intent)

        }

    }
}
