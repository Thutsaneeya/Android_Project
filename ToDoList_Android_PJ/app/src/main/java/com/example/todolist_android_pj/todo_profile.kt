package com.example.todolist_android_pj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager

/**
 * A simple [Fragment] subclass.
 */
class todo_profile : Fragment() {
    var PhotoURL : String = ""
    var Name : String = ""

    fun newInstance(url: String,name : String): todo_profile  {

        val profile = todo_profile () //call fragment_home
        val bundle = Bundle()

        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        profile.setArguments(bundle)

        return profile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_todo_profile, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        val login_button2 = view.findViewById(R.id.login_button2) as Button
        val button : Button = view.findViewById(R.id.lets_go) as Button

//start load img fb
        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)
//end load img fb

        tvName.setText(Name) //set name from fb

//start log out fb
        login_button2.setOnClickListener{

            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()
        }
//      end  log out fb
//         Inflate the layout for this fragment

        //start let's go button
        button.setOnClickListener {

                val todo_main = todo_main()
                val fm =fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout,todo_main,"fragment_todo_main")
                transaction.addToBackStack("todo_main")
                transaction.commit()
        }//End let's go button

        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()//img fb
            Name = bundle.getString("Name").toString()//name fb
        }
    }
}
