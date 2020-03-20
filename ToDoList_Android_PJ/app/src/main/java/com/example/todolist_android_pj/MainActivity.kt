package com.example.todolist_android_pj

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_SIGNATURES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debugHashKey()

        val authen = todo_login()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.layout, authen, "fragment_todo_login")
        transaction.addToBackStack("fragment_todo_login")
        transaction.commit()

    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun debugHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.todolist_android_pj",
                GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {
        }

    }
//    override fun onBackPressed() {
//
//        val manager = supportFragmentManager.findFragmentById(R.id.layout)
//        if (manager is fragment_recycler_view ) {
//
//            finish()
//
//        }
//        else{
//            super.onBackPressed()
//        }
//
//    }
}
