package com.example.myapplication



import android.app.ProgressDialog.show
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
import android.support.v7.app.AlertDialog
import android.content.DialogInterface
import android.icu.util.Calendar
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity(){


    private var currentDir : File = Environment.getExternalStorageDirectory()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //showCurrentDate()

        //showPrice()
        //insertText(this, "nameText", "33")



        val insertViewInsert = findViewById<Button>(R.id.insert)

        insertViewInsert.setOnClickListener {

            //Androidアプリ開発入門（p.185参照）

            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }




        val insertViewWallet = findViewById<TextView>(R.id.mokuhyo_price)

        insertViewWallet.setOnClickListener {
            //Androidアプリ開発入門（p.185参照）
            val intent = Intent(this, ZandakaActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()


        var textsM = queryTexts(this)
        var texts = makeList(this, textsM[2][0])


        val cl = Calendar.getInstance()
        val nowMonth  = cl.get(Calendar.MONTH) + 1
        var buf0 = texts[2][0]


        val monthMinus = findViewById<TextView>(R.id.monthMinus)
        monthMinus.setOnClickListener {

            val buf1 = getDatePlus(buf0, -1)
            buf0 = buf1
            texts = makeList(this, buf0)

            if(getMonth(buf0)==nowMonth){
                showPrice(texts, buf0, 0)
            }else{
                showPrice(texts, buf0, 1)
            }

            val zankin_titleView = findViewById<TextView>(R.id.zankin_title)
            zankin_titleView.text = getMonth(buf0).toString()+"月の残高"

        }

        val monthPlus = findViewById<TextView>(R.id.monthPlus)
        monthPlus.setOnClickListener {

            val buf2 = getDatePlus(buf0, 1)
            buf0 = buf2
            texts = makeList(this, buf0)

            if(getMonth(buf0)==nowMonth){
                showPrice(texts, buf0, 0)

            }else{
                showPrice(texts, buf0, 1)

            }

            val zankin_titleView = findViewById<TextView>(R.id.zankin_title)
            zankin_titleView.text = getMonth(buf0).toString()+"月の残高"


        }

        if(getMonth(buf0)==nowMonth){
            showPrice(texts, buf0, 0)
        }else{
            showPrice(texts, buf0, 1)
        }

        val zankin_titleView = findViewById<TextView>(R.id.zankin_title)
        zankin_titleView.text = getMonth(buf0).toString()+"月の残高"


        //Toast.makeText(applicationContext, "onResume()", Toast.LENGTH_SHORT).show()

    }




    private fun showPrice(texts :List<List<String>>, buf0 :String, flag :Int){




        var yearAndmonth = findViewById<TextView>(R.id.month)

        yearAndmonth.text = getDate(buf0)
        //Toast.makeText(applicationContext, buf0, Toast.LENGTH_SHORT).show()


        showZandaka(flag, texts)

        //val myMutableIntList1 = mutableListOf<String>("1","2","3")

        //val myIntList2 =  mutableListOf<String>("7","8","9")

        //val my2DIntList : List<MutableList<String>> = listOf(myMutableIntList1,myIntList2)

        //val even  = Array(100, { Array(3, {it})})

        var list =findViewById<ListView>(R.id.listView)

        list.adapter = DataAdapter(this, texts)

        list.setOnItemClickListener {_,_, position, _ ->

            AlertDialog.Builder(this).apply {
                setTitle("Title")
                setMessage("削除しますか？")
                setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    val pos = texts[4][position]
                    deleteText(applicationContext, pos)
                    //adapter.notifyDataSetChanged()

                    //val textsM = queryTexts(applicationContext)
                    //texts = queryTexts(applicationContext)
                    //val textsA = makeList(applicationContext, textsM[2][0])
                    val textsA = makeList(applicationContext, buf0)

                    //val newlist =findViewById<ListView>(R.id.listView)
                    list.adapter =  DataAdapter(applicationContext, textsA)
                    showZandaka(flag, textsA)
                    //yearAndmonth.text = getDate(textsA[2][0])

                })


                setNegativeButton("Cancel", null)
                show()


            }

            /*texts = queryTexts(this)
            val newlist =findViewById<ListView>(R.id.listView)
            newlist. =  DataAdapter(this, texts)*/
        }
        //list.removeAllViews()

    }

    fun showZandaka(flag :Int,  texts :List<List<String>>){

        //val pref_zandaka = getSharedPreferences("file_name", Context.MODE_PRIVATE)
        //val storedTextzandaka = pref_wallet.getString("key", "0")
        val exp_zandaka = findViewById<TextView>(R.id.zankin_price)
        val pref_wallet = getSharedPreferences("file_wallet", Context.MODE_PRIVATE)
        val storedTextwallet = pref_wallet.getString("key", "0")
        val exp_wallet = findViewById<TextView>(R.id.mokuhyo_price)




        //exp_zandaka.text = storedText

        var textssize = texts[3].size

        var zandaka = Integer.parseInt(storedTextwallet)

        if(flag == 1){
            zandaka = 0
        }

        exp_wallet.text = zandaka.toString()+"円"

        for (i in 0..textssize-1) {
            if( texts[3][i] == "1"){
                zandaka += Integer.parseInt(texts[1][i])
            }else{
                zandaka -= Integer.parseInt(texts[1][i])
            }
        }

        val storedText = zandaka.toString()
        exp_zandaka.text = storedText+"円"

    }

    fun getDate(date :String) :String{

        val datelong = date.toLong()
        val yearlong = datelong/10000
        val monthlong = (datelong - yearlong*10000) / 100
        val display = yearlong.toString() + "年" + monthlong.toString() + "月"

        return display
    }

    fun getMonth(date :String) :Int{

        val datelong = parseInt(date)
        val yearlong = datelong/10000
        val monthlong = (datelong - yearlong*10000) / 100


        return monthlong
    }

    fun getDatePlus(date :String,i :Int ) :String{

        val datelong = date.toLong()
        var yearlong = datelong/10000
        var monthlong = ((datelong - yearlong*10000) / 100) + i

        if(monthlong > 12){
            monthlong = monthlong - 12
            yearlong = yearlong + 1
        }else if(monthlong <= 0){
            monthlong = monthlong + 12
            yearlong = yearlong - 1
        }

        val display = (yearlong*10000+monthlong*100).toString()

        return display
    }

    fun makeList(context :Context, date :String) :List<List<String>>{

        val datelong2 = parseInt(date)
        val yearlong2  = datelong2/10000
        val monthlong2  = (datelong2 - yearlong2*10000) / 100

        val returnDatas = selectInDay(context, yearlong2, monthlong2)

        return returnDatas

    }


}












