package com.example.test0714


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.io.File


class MainActivity : AppCompatActivity(){


    private var currentDir : File = Environment.getExternalStorageDirectory()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //showCurrentDate()

        //showPrice()
        //insertText(this, "nameText", "33")

        showPrice()

        val insertViewInsert = findViewById<Button>(R.id.insert)

        insertViewInsert.setOnClickListener {

            //Androidアプリ開発入門（p.185参照）

            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }

        val insertViewWallet = findViewById<TextView>(R.id.mokuhyo_price)

        insertViewWallet.setOnClickListener {
            //Androidアプリ開発入門（p.185参照）
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }


        val insertViewZankin = findViewById<TextView>(R.id.zankin_price)

        insertViewZankin.setOnClickListener {
            //Androidアプリ開発入門（p.185参照）
            val intent = Intent(this, ZandakaActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        showPrice()


        val pref_zandaka = getSharedPreferences("file_name", Context.MODE_PRIVATE)
        val storedText = pref_zandaka.getString("key", "未登録")
        val exp_zandaka = findViewById<TextView>(R.id.zankin_price)
        exp_zandaka.text = storedText


        val pref_wallet = getSharedPreferences("file_wallet", Context.MODE_PRIVATE)
        val storedTextwallet = pref_wallet.getString("key", "未登録")
        val exp_wallet = findViewById<TextView>(R.id.mokuhyo_price)
        exp_wallet.text = storedTextwallet

        Toast.makeText(applicationContext, "onResume()", Toast.LENGTH_SHORT).show()

    }




    private fun showPrice(){

        val texts = queryTexts(this)

        //val myMutableIntList1 = mutableListOf<String>("1","2","3")

        //val myIntList2 =  mutableListOf<String>("7","8","9")

        //val my2DIntList : List<MutableList<String>> = listOf(myMutableIntList1,myIntList2)

        //val even  = Array(100, { Array(3, {it})})

        val list =findViewById<ListView>(R.id.listView)

        list.adapter = DataAdapter(this, texts)

    }
    
}





