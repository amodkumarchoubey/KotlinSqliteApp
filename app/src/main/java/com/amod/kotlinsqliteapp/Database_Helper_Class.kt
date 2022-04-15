package com.amod.kotlinsqliteapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


class Database_Helper_Class(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                FNAME + " TEXT," + LNAME + " TEXT," +
                GENDER + " TEXT,"+ STANDARD +" TEXT," + CURRENT_TIME + " TEXT);"
        db!!.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db!!.execSQL(DROP_TABLE)
        onCreate(db)
    }



    fun onStoreData(user:User) : Boolean {
        val db : SQLiteDatabase=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(FNAME,user.fname)
        contentValues.put(LNAME,user.lname)
        contentValues.put(GENDER,user.gender)
        contentValues.put(STANDARD,user.standard)
        contentValues.put(CURRENT_TIME,user.record)
        Log.e("contentValues","====>"+contentValues.toString());
        val insert_data = db.insert(TABLE_NAME,null,contentValues)
        Log.e("onStoreData","inserted====>"+insert_data);
        db.close()
        return !insert_data.equals(-1)
    }


    @SuppressLint("Range")
    fun getUserData() : List<User> {
        Log.e("inside","getUserData")
        val userList = ArrayList<User>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                Log.e("inside","cursor is===>")
                val user = User()
                user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                user.fname = cursor.getString(cursor.getColumnIndex(FNAME))
                user.lname = cursor.getString(cursor.getColumnIndex(LNAME))
                user.gender = cursor.getString(cursor.getColumnIndex(GENDER))
                user.standard = cursor.getString(cursor.getColumnIndex(STANDARD))
                user.record = cursor.getString(cursor.getColumnIndex(CURRENT_TIME))
                userList.add(user)
            }
        }
        cursor.close()
        return userList
        Log.e("getUserData","Size is===>"+userList.size)
    }


    val user: List<User>
        @SuppressLint("Range")
        get() {
            Log.e("inside","get() ")
            val userList = ArrayList<User>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val user = User()
                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.fname = cursor.getString(cursor.getColumnIndex(FNAME))
                    user.lname = cursor.getString(cursor.getColumnIndex(LNAME))
                    user.gender = cursor.getString(cursor.getColumnIndex(GENDER))
                    user.standard = cursor.getString(cursor.getColumnIndex(STANDARD))
                    user.record = cursor.getString(cursor.getColumnIndex(CURRENT_TIME))
                    userList.add(user)
                }
            }
            cursor.close()
            return userList
            Log.e("   get()","Size is===>"+userList.size)

        }

    fun updateUser(user : User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FNAME,user.fname)
        values.put(LNAME,user.lname)
        values.put(GENDER,user.gender)
        values.put(STANDARD,user.standard)
        values.put(CURRENT_TIME,user.record)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(user.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteUser(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }


    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "MyTasks"
        private val TABLE_NAME = "Tasks"
        private val ID = "Id"
        private val FNAME = "FName"
        private val LNAME= "LName"
        private val GENDER = "Gender"
        private val STANDARD="Standard"
        private val CURRENT_TIME="Record"

    }

}