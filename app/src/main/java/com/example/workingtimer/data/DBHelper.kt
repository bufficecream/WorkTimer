package com.example.workingtimer.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.workingtimer.manage.SingletonHolder

val DATABASE_NAME = "WorkTimerSQLite.db"
val DATABASE_VERSION = 1

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val COL_ID = "_id"
    val COL_DATE = "date"
    val COL_ABS_TIME = "absolute_time"
    val COL_ORDER_ID = "order_id"
    val COL_LAP_TIME = "lap_time"
    val COL_TOTAL_TIME = "total_time"
    val COL_WORK_NAME = "work_name"
    val COL_COLOR = "color"

    val TABLE_WORK = "work"
    val TABLE_COMMON_WORK = "commonWork"

    companion object : SingletonHolder<DBHelper, Context>(::DBHelper)

    override fun onCreate(db: SQLiteDatabase?) {

        createWorkTable(db)
        createCommonWorksTable(db)
    }

    private fun createWorkTable(db: SQLiteDatabase?){

        var cmd = "CREATE TABLE " +
                TABLE_WORK + "(" +
                COL_ID+" INTEGER PRIMARY KEY NOT NULL, " +
                //TODO check the appropriate type for DATE
                COL_DATE+" TEXT, " +
                COL_ABS_TIME+" TEXT, "+
                COL_ORDER_ID +" INTEGER " +
                COL_LAP_TIME +" TEXT " +
                COL_TOTAL_TIME +" TEXT " +
                COL_WORK_NAME +" TEXT " +
                COL_COLOR +" TEXT " +
                ");"

        db?.execSQL(cmd);
    }

    private fun createCommonWorksTable(db: SQLiteDatabase?){

        var cmd = "CREATE TABLE " +
                TABLE_COMMON_WORK + "(" +
                COL_ID+" INTEGER PRIMARY KEY NOT NULL, " +
                COL_WORK_NAME +" TEXT " +
                COL_COLOR +" TEXT " +
                ");"

        db?.execSQL(cmd);
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}