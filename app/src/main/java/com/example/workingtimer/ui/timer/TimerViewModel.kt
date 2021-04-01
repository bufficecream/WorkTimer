package com.example.workingtimer.ui.timer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TimerViewModel : ViewModel() {

    val TAG: String = "TimerViewModel"

    private var timerDataModel: TimerDataModel = TimerDataModel()

    private lateinit var tickerChannel: ReceiveChannel<Unit>
//    = ticker(delayMillis = 1, initialDelayMillis = 0)
    var tMilliSec = 0
    private var timeStr: String = ""

    private var _textTimer = MutableLiveData<String>().apply {
        value = timerDataModel.TIME_ORIGIN
    }
    var textTimer: LiveData<String> = _textTimer

    private var _textLapRecord = MutableLiveData<String>().apply {
        value = ""
    }
    var textLapRecord: LiveData<String> = _textLapRecord


    fun play_or_pause(state_play: Boolean){
        if(state_play)
            play() else pause()
    }

    fun play(){

        Log.d(TAG, "play")

        tickerChannel = ticker(delayMillis = 1, initialDelayMillis = 0)

        var pre_tSec = -1

        viewModelScope.launch {
            for(event in tickerChannel){

                tMilliSec++
                var tSec = tMilliSec / 1000

                if(pre_tSec != tSec){

                    Log.d(TAG, ""+tSec)

                    pre_tSec = tSec

                    computeTimeStr(tSec)

                    withContext(Dispatchers.IO) {

                        _textTimer.postValue(timeStr)
                    }
                }
            }
        }
    }

    fun updateTime(tSec: Int){

        computeTimeStr(tSec)

        _textTimer.postValue(timeStr)
    }

    fun playBackground(){
        Log.d(TAG, "playBackground")

        tickerChannel = ticker(delayMillis = 1, initialDelayMillis = 0)

        var pre_tSec = -1

        GlobalScope.launch {
            for(event in tickerChannel){

                tMilliSec++
                var tSec = tMilliSec / 1000

                if(pre_tSec != tSec){

                    Log.d(TAG, ""+tSec)

                    pre_tSec = tSec

                    computeTimeStr(tSec)
                }
            }
        }
    }

    private fun computeTimeStr(tSec: Int){

        val cSec = tSec%60
        val cMin = (tSec/60)%60
        val cHr = (tSec/3600)%24

        timeStr = ""
        lateinit var sHr: String
        sHr = if(cHr < 10) "0" else ""
        timeStr = sHr + cHr

        lateinit var sMin: String
        sMin = if(cMin < 10) ":0" else ":"
        timeStr += sMin + cMin

        lateinit var sSec: String
        sSec = if(cSec < 10) ":0" else ":"
        timeStr += sSec + cSec
    }

    private fun pause(){

        Log.d(TAG, "pause")

        cancelTicker()

        timerDataModel.storeInDB()
    }

    fun reset(){

        Log.d(TAG, "reset")

        cancelTicker()

        tMilliSec = 0

        _textTimer.postValue(timerDataModel.TIME_ORIGIN)
    }

    fun cancelTicker(){

        try {
            tickerChannel.cancel()
        } catch (e: UninitializedPropertyAccessException) {
            Log.e(TAG, "tickerChannel haven't been created yet")
        }

    }

    fun lap(){

        Log.d(TAG, "lap")

        //TODO create a new line for the previous lap

    }


}