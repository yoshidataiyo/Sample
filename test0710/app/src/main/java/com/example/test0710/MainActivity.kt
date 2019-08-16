package com.example.test0710


import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt = findViewById<Button>(R.id.button)

        bt.setOnClickListener {

            //Androidアプリ開発入門（p.185参照）

            //tv.setTextColor(Color.parseColor("#FF00C0"))

        }
        //switchの扱い　p.445
        val sb = findViewById<Switch>(R.id.switch1)
        sb.isChecked = false
        changeTextcolor(0)

        sb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                changeTextcolor(1)
            } else {
                changeTextcolor(0)
            }
        }

    }
      fun changeTextcolor(num: Int){
            val tv = findViewById<TextView>(R.id.textView)
            if(num == 1){
                //tv.setTextColor(Color.parseColor("colorExpenditure"))
                tv.setTextColor(Color.parseColor("#0000FF"))
            }
            else if(num == 0){
                //tv.setTextColor(Color.parseColor("@color/colorIncome"))
                tv.setTextColor(Color.parseColor("#FF0000"))
            }

        }


}