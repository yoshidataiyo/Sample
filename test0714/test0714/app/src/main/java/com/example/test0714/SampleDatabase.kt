package com.example.test0714


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Calendar

private const val DB_NAME = "SampleDatabase"
private const val DB_VERSION = 1

class SampleDBOpenHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        /*db?.execSQL("CREATE TABLE texts (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " text TEXT NOT NULL, "+
                " text2 TEXT NOT NULL, "+
                " price TEXT NOT NULL, "+
                " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")*/


        db?.execSQL(
            """
            CREATE TABLE texts(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            text TEXT NOT NULL,
            price INTEGER NOT NULL,
            time INTEGER NOT NULL,
            money INTEGER NOT NULL,
            idate INTEGER NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
        """
        )
    }


    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int){

    }

}

fun queryTexts(context: Context) : List<List<String>> {
    val database = SampleDBOpenHelper(context).readableDatabase
    val cursor =database.query("texts", null, null, null, null, null, "created_at DESC")
    val texts = mutableListOf<String>()
    val prices = mutableListOf<String>()
    val times = mutableListOf<String>()
    val moneys = mutableListOf<String>()
    val idates = mutableListOf<String>()

    cursor.use {
        while(cursor.moveToNext()) {
            val text = cursor.getString(cursor.getColumnIndex("text"))
            texts.add(text)
            val price = cursor.getLong(cursor.getColumnIndex("price"))
            prices.add(price.toString())
            val time = cursor.getLong(cursor.getColumnIndex("time"))
            times.add(time.toString())
            val money = cursor.getLong(cursor.getColumnIndex("money"))
            moneys.add(money.toString())
            val idate = cursor.getLong(cursor.getColumnIndex("idate"))
            idates.add(idate.toString())
        }
    }

    val my2DIntList : List<List<String>> = listOf(texts,prices,times,moneys,idates)


    database.close()



    return my2DIntList

}

fun insertText(context: Context, text: String, price: String, time: String, money: Int, idate: Long){
    val database = SampleDBOpenHelper(context).writableDatabase
    database.use {db->
        val record = ContentValues().apply {
            put("text", text)
            put("price", price.toLong())
            put("time",   time.toLong())
            put("money", money.toLong())
            put("idate", idate)
        }
        db.insert("texts", null, record)
    }
}

fun deleteText(context: Context, pos: String){
    val database = SampleDBOpenHelper(context).writableDatabase

    database.delete("texts",  "idate = ?", arrayOf(pos))

    database.close()

}


class TimeRecord(val id : Long, val text : String, val price : Long, val time :Long, val money :Long, val idate :Long)


fun selectInDay(context: Context, year: Int, month: Int, day: Int) : List<TimeRecord>{

    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, 0, 0, 0)

    val from =calendar.time.time.toString()
    calendar.add(Calendar.DATE, 1)
    val to = calendar.time.time.toString()

    val database = SampleDBOpenHelper(context).readableDatabase
    val cursor = database.query("texts", null, "time >= ? AND time < ?", arrayOf(from, to), null, null, "time DESC")

    val times = mutableListOf<TimeRecord>()
    cursor.use {
        while(cursor.moveToNext()) {
            val place = TimeRecord(
                cursor.getLong(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("text")),
                cursor.getLong(cursor.getColumnIndex("price")),
                cursor.getLong(cursor.getColumnIndex("time")),
                cursor.getLong(cursor.getColumnIndex("money")),
                cursor.getLong(cursor.getColumnIndex("idate"))
            )
            times.add(place)
        }
    }
    database.close()
    return times
}

