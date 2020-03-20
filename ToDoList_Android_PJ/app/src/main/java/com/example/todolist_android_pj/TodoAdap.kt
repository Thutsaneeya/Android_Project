package com.example.todolist_android_pj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import org.json.JSONArray

class TodoAdap (act : FragmentActivity, val dataSource: JSONArray) : BaseAdapter(){

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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder : ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.layout_listview, parent, false)

            // 3
            holder = ViewHolder()
            holder.NoteTextView = view.findViewById(R.id.username) as TextView
//            holder.detailTextView = view.findViewById(R.id.text) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val NoteTextView = holder.NoteTextView
//        val detailTextView = holder.detailTextView

        NoteTextView.setText( dataSource.getJSONObject(position).getString("Note").toString() )

        return view
    }

    private class ViewHolder {

        lateinit var NoteTextView: TextView
//        lateinit var detailTextView: TextView

    }

}
