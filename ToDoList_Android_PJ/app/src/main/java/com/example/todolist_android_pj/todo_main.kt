package com.example.todolist_android_pj

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Constants
import kotlinx.android.synthetic.main.fragment_todo_main.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class todo_main : Fragment() {

    val mRootRef = FirebaseDatabase.getInstance().getReference()
    val mUsersRef = mRootRef.child("Note")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_todo_main, container, false)
        val fab = view.findViewById<View>(R.id.fab) as FloatingActionButton
        val button : Button = view.findViewById(R.id.recy_btn) as Button
// start fab button

        fab.setOnClickListener { view ->
            //Show Dialog here to add new Item
            addNewItemDialog()
        }

        //        end fab button

//  start get item from realtime database
          mUsersRef.addValueEventListener(object : ValueEventListener {
              override fun onDataChange(dataSnapshot: DataSnapshot){

                  val list = JSONArray()

                  val listView = view.findViewById<ListView>(R.id.listView)

                  for (ds in dataSnapshot.children) {

                      val jObject = JSONObject()

                      val item = ds.child("item").getValue(String::class.java)!!
//                      val text = ds.child("text").getValue(String::class.java)!!

                      jObject.put("key", ds.key)
                      jObject.put("Note", item)
//                      jObject.put("text", text)

                      list.put(jObject)

                  }
                  val adapter = TodoAdap(activity!!, list)

                  listView.adapter = adapter
              }
              override fun onCancelled(error: DatabaseError) {

              }
          })
//  end get item from realtime database

//        start recy button to recycler view

        button.setOnClickListener {
            val todo_recy_view = todo_recy_view()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout,todo_recy_view,"fragment_todo_recy_view")
            transaction.addToBackStack("fragment_todo_recy_view")
            transaction.commit()

            Toast.makeText(context,"This is EXO view", Toast.LENGTH_SHORT).show()
        }

        //        end recy button to recycler view
        return view
  }

    class ToDoItem {
        companion object Factory {
            fun create(): ToDoItem = ToDoItem()
        }
        var objectId: String? = null
        var itemText: String? = null
        var done: Boolean? = false
    }

//    start data class

    data class FriendlyMessage(
        var item: String? = ""
    )

//    end data class

//start dialog insert item***************************************************************************

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this.context)
        val itemEditText = EditText(this.context)
        alert.setMessage("Enter To Do item")
        alert.setTitle("New iTem")
        alert.setView(itemEditText)
        alert.setPositiveButton("SUBMIT") { dialog, positiveButton ->

            val todoItem = ToDoItem.create()
            val item = itemEditText.text.toString()

            //start send value to database realtime**********************************************

            val friendlyMessage = FriendlyMessage(item)
            mUsersRef.push().setValue(friendlyMessage)

            //end send value to database realtime**********************************************

            Toast.makeText(context,"Item saved" , Toast.LENGTH_SHORT).show()

            dialog.dismiss()

        }
        alert.setNegativeButton("CANCEL"){dialog, positiveButton ->
            dialog.dismiss()
        }
        alert.show()
    }

    //end dialog insert item***************************************************************************
}
