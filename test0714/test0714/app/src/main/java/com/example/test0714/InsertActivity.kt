package com.example.test0714

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.*
import java.util.*


class InsertActivity : AppCompatActivity() ,DatePickerDialog.OnDateSetListener {

    private var currentDate = Calendar.getInstance()
    private var flag = 0
    private var moneyswitch = 0
    private val datalogging = Datalogging()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)


        var isValid = true
        val sb = findViewById<Switch>(R.id.switch1)
        sb.isChecked = false
        changeAppearance()


        sb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                moneyswitch = 1
                changeAppearance()
            } else {
                moneyswitch = 0
                changeAppearance()
            }
        }



        val insertView1 = findViewById<Button>(R.id.add)

        insertView1.setOnClickListener {
            val priceEditText = findViewById<EditText>(R.id.editPrice)
            val priceText = priceEditText.text.toString()
            val nameEditText = findViewById<EditText>(R.id.editName)
            val nameText = nameEditText.text.toString()


            if(priceText.isEmpty() || nameText.isEmpty()){

                isValid = false

                Toast.makeText(applicationContext, "入力不備のため追加不可", Toast.LENGTH_SHORT).show()
            }



            if(isValid) {
                val price = Integer.parseInt(priceText)

                //showCurrentDate(nameText, priceText)



                datalogging.getNP(nameText, priceText, moneyswitch)

                showCurrentDate()
                //データベースへ保存p.425くらい

                //startActivity(intent)
                //intent.putExtra("price", price)
                //intent.putExtra("name", nameText)

                //insertText(this, nameText, priceText, currentDate.time.toString())

            }
        }

    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        currentDate.set(year, month, dayOfMonth)

        //showCurrentDate()

        Toast.makeText(applicationContext, currentDate.time.toString(), Toast.LENGTH_LONG).show()

        flag = 1
        //insertText(this, nameText, price.toString(), currentDate.time.toString())
        datalogging.getDate(dayOfMonth, month, year)

        moveToNext()

    }

    private fun showCurrentDate() {

        val dialog = DatePickerFragment()
        dialog.arguments = Bundle().apply {
            putSerializable("current", currentDate) }
        dialog.show(supportFragmentManager, "calendar")

    }

    private fun  moveToNext(){
        if (flag == 1) {
            datalogging.logData(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changeAppearance(){
        val textview4 = findViewById<TextView>(R.id.textView4)
        val textview5 = findViewById<TextView>(R.id.textView5)

        val large = 28.00F//:Float
        val small = 14.00F//型はFloat型指定

        if(moneyswitch==0){
            textview4.setTextSize(large)
            textview5.setTextSize(small)
        }
        else{
            textview4.setTextSize(small)
            textview5.setTextSize(large)
        }
    }

}

class Datalogging(){


    private var Name = "null"
    private var Price = "null"
    private var Date = "null"
    private var SwitchChan = 0

    fun getNP(name: String, price: String, switchchan: Int){
        Price = price
        Name = name
        SwitchChan = switchchan
    }

    fun getDate(date: Int, month :Int,year :Int){
        Date = (date + (month +1) * 100 + year * 10000).toString()
    }

    fun logData(context: Context){

        val cl =Calendar.getInstance()
        //val logtime = cl.setTimeInMillis(milliSeconds)
        val logtime = cl.time.time
        insertText(context, Name, Price, Date, SwitchChan, logtime)

    }

}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var calendar : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = arguments?.getSerializable("current") as? Calendar ?: Calendar.getInstance()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(context, this, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE])
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if(context is DatePickerDialog.OnDateSetListener) {
            (context as DatePickerDialog.OnDateSetListener).onDateSet(view, year, month, day)
        }
    }

}



