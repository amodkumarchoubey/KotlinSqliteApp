package com.amod.kotlinsqliteapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class Adapter_class(val mainActivity: MainActivity, val arrayList: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<Adapter_class.ViewHolder>() {
lateinit var helperClass : Database_Helper_Class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vi=LayoutInflater.from(mainActivity).inflate(R.layout.adapter_item_layout,parent,false)
        return ViewHolder(vi)
    }

    override fun getItemCount(): Int {
        return arrayList.size
        Log.d("size",arrayList.size.toString())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=arrayList.get(position).get(FNAME)+"  "+arrayList.get(position).get(LNAME)
        holder.gender.text=arrayList.get(position).get(GENDER)
        holder.standard.text=arrayList.get(position).get(STANDARD)
        holder.time.text=arrayList.get(position).get(CURRENT_TIME)
        helperClass= Database_Helper_Class(mainActivity)
        Log.d("hiiii", arrayList.toString())

        holder.update.setOnClickListener {
            val intent = Intent(mainActivity, UpdateActivity::class.java)
            intent.putExtra(ID, arrayList.get(position).get(ID))
            intent.putExtra(FNAME,arrayList.get(position).get(FNAME))
            intent.putExtra(LNAME, arrayList.get(position).get(LNAME))
            intent.putExtra(GENDER,arrayList.get(position).get(GENDER))
            intent.putExtra(STANDARD, arrayList.get(position).get(STANDARD))
            intent.putExtra(CURRENT_TIME, arrayList.get(position).get(CURRENT_TIME))
            mainActivity.startActivity(intent)
        }

        holder.delete.setOnClickListener {
           val result : Boolean = helperClass.deleteUser(Integer.parseInt(arrayList.get(position).get(ID)))

            when{
                result->{
                    Toast.makeText(mainActivity,"Data deleted Successfully..", Toast.LENGTH_LONG).show()
                    val intent = Intent(mainActivity, MainActivity::class.java)
                    mainActivity.startActivity(intent)
                }
                else-> Toast.makeText(mainActivity,"Failed to delete data", Toast.LENGTH_LONG).show()
            }

        }
    }

    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val name=item.findViewById<TextView>(R.id.txt_name)
        val gender=item.findViewById<TextView>(R.id.txt_gender)
        val standard=item.findViewById<TextView>(R.id.txt_standard)
        val time=item.findViewById<TextView>(R.id.txt_time)
        val update=item.findViewById<Button>(R.id.btn_update)
        val delete=item.findViewById<Button>(R.id.btn_delete)
    }

    companion object {
        private val ID = "Id"
        private val FNAME = "FName"
        private val LNAME= "LName"
        private val GENDER = "Gender"
        private val STANDARD="Standard"
        private val CURRENT_TIME="Record"

    }
}