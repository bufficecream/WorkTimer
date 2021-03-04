package com.example.workingtimer.ui.timer

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workingtimer.R

class TimerWorker(val context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams) {

    private val tMilliSecStr: String = "tMilliSec"

    override fun doWork(): Result {

        val sharedPref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

        var tMilliSec:Int = sharedPref.getInt(tMilliSecStr, 0)

        tMilliSec++

//        sharedPref.edit().putInt(tMilliSecStr, tMilliSec).apply()

        val milliSecData: Data = Data.Builder().putInt(tMilliSecStr, 0).build()

        return Result.success(milliSecData)
    }


}