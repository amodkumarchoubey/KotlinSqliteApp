package com.amod.kotlinsqliteapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var databaseHelperClass: Database_Helper_Class
    lateinit var recyclerView: RecyclerView
    lateinit var adapterClass: Adapter_class
    lateinit var txt_nodata: TextView
    lateinit var btn_adddata: Button
    var hashMapArrayList: ArrayList<HashMap<String, String>> = ArrayList()
    var list: List<User>? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        txt_nodata = (findViewById(R.id.txt_nodata))
        btn_adddata = (findViewById(R.id.btn_adddata))
        btn_adddata.setOnClickListener(this)
        recyclerView = findViewById(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        databaseHelperClass = Database_Helper_Class(this)

    }



    override fun onResume() {
        readData()
        registerForContextMenu(recyclerView)
        super.onResume()
    }

    private fun readData() {
        val list_ = databaseHelperClass.getUserData()
        Log.e("list_list_list_", "Size is===>" + list_.size)
        val list = databaseHelperClass.user
        Log.e("list", "Size is===>" + list.size)
        hashMapArrayList.clear()
        if (list != null && list.size > 0) {
            setvisibility()
            for (user: User in list) {
                val hashMap = HashMap<String, String>()
                hashMap.put(ID, user.id.toString())
                hashMap.put(FNAME, user.fname)
                hashMap.put(LNAME, user.lname)
                hashMap.put(GENDER, user.gender)
                hashMap.put(STANDARD, user.standard)
                hashMap.put(CURRENT_TIME, user.record)
                hashMapArrayList.add(hashMap)
            }
            adapterClass = Adapter_class(this, hashMapArrayList)
            recyclerView.adapter = adapterClass
            Log.d("array", hashMapArrayList.toString())
            Toast.makeText(this, "Data Received..", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Data not found", Toast.LENGTH_LONG).show()

        }
    }

    private fun setvisibility() {
        txt_nodata.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

    }

    companion object {
        private val ID = "Id"
        private val FNAME = "FName"
        private val LNAME = "LName"
        private val GENDER = "Gender"
        private val STANDARD = "Standard"
        private val CURRENT_TIME = "Record"

    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, Insert_Activity::class.java)
        startActivity(intent)
    }

}
