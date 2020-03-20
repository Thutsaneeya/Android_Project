package com.example.todolist_android_pj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import org.json.JSONArray

class Adapter (act : FragmentActivity, val dataSource: JSONArray) : BaseAdapter() {

    private val activity : FragmentActivity = act
    private val thiscontext: Context = act.baseContext
    private val inflater: LayoutInflater = thiscontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.length()
    }

    override fun getItem(position: Int): Any {
        return dataSource.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val holder : ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.layout_listview, parent, false)

            // 3
            holder = ViewHolder()
            holder.userTextView = view.findViewById(R.id.username) as TextView
            holder.detailTextView = view.findViewById(R.id.text) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val userTextView = holder.userTextView
        val detailTextView = holder.detailTextView

        userTextView.setText( dataSource.getJSONObject(position).getString("username").toString() )
        detailTextView.setText( dataSource.getJSONObject(position).getString("text").toString() )

//        view.setOnClickListener{
//
//            val Edit = Edit().newInstance(dataSource.getJSONObject(position).getString("key").toString(),dataSource.getJSONObject(position).getString("username").toString(),dataSource.getJSONObject(position).getString("text").toString())
//            val fm = activity.supportFragmentManager
//            val transaction : FragmentTransaction = fm.beginTransaction()
//            transaction.replace(R.id.edit, Edit,"fragment_Edit")
//            transaction.addToBackStack("fragment_Edit")
//            transaction.commit()
//
//            //เตรียม implement ตอนแก้ไขข้อมูล
//        }

        return view
    }

    private class ViewHolder {

        lateinit var userTextView: TextView
        lateinit var detailTextView: TextView

    }


}
