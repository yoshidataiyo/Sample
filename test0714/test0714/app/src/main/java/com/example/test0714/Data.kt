package com.example.test0714


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView



class DataAdapter(private val context: Context, private val Datas: List<List<String>> ) : BaseAdapter(){

    private val inflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?:createView(parent)

        val DatasId = getItem(position)

        val viewHolder = view.tag as ViewHolder


        viewHolder.date.text = getDate(Datas[2][position])
        viewHolder.name.text = Datas[0][position]
        viewHolder.price.text = Datas[1][position]

        if(Datas[3][position].toInt() == 0) {
            viewHolder.price.setTextColor(Color.RED)
        }
        else{
            viewHolder.price.setTextColor(Color.BLUE)
        }

        return view

    }

    override fun getItem(position: Int) = Datas[0][position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = Datas[0].size

    private fun createView(parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.raw, parent, false )
        view.tag = ViewHolder(view)
        return view
    }

    private class ViewHolder(view: View){
        val date = view.findViewById<TextView>(R.id.raw1)
        val name = view.findViewById<TextView>(R.id.raw2)
        val price = view.findViewById<TextView>(R.id.raw3)
    }

    fun getDate(date :String) :String{

        val datelong = date.toLong()
        val yearlong = datelong/10000
        val monthlong = (datelong - yearlong*10000) / 100
        val daylong = datelong % 100
        val display = monthlong.toString() + "/" + daylong.toString()

        return display
    }

}