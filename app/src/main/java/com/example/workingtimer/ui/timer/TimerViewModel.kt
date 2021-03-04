package com.example.workingtimer.ui.timer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.util.*

class TimerViewModel : ViewModel() {

    val TAG: String = "TimerViewModel"

    private var timerDataModel: TimerDataModel = TimerDataModel()

    private var tickerChannel = ticker(delayMillis = 1, initialDelayMillis = 0)
    private var tMilliSec = 0
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

    private fun play(){

        Log.d(TAG, "play")

        var pre_tSec = -1

        GlobalScope.launch {
            for(event in tickerChannel){

                tMilliSec++
                val tSec = tMilliSec / 1000

                if(pre_tSec != tSec){

                    pre_tSec = tSec

                    val cSec = tSec%60
                    val cMin = (tSec/60)%60
                    val cHr = (tSec/3600)%24

                    timeStr = ""
                    lateinit var sHr: String
                    if(cHr < 10) sHr = "0" else sHr = ""
                    timeStr = sHr + cHr

                    lateinit var sMin: String
                    if(cMin < 10) sMin = ":0" else sMin = ":"
                    timeStr += sMin + cMin

                    lateinit var sSec: String
                    if(cSec < 10) sSec = ":0" else sSec = ":"
                    timeStr += sSec + cSec

                    _textTimer.postValue(timeStr)
                }
            }
        }
    }

    private fun pause(){

        Log.d(TAG, "pause")

        cancelTicker()
    }

    fun reset(){

        Log.d(TAG, "reset")

        cancelTicker()

        tMilliSec = 0

        _textTimer.postValue(timerDataModel.TIME_ORIGIN)
    }

    private fun cancelTicker(){

        tickerChannel.cancel()
        //TODO, weird that the ticker being reinitialized
        tickerChannel = ticker(delayMillis = 1, initialDelayMillis = 0)
    }

    fun lap(){

        Log.d(TAG, "lap")

        //TODO create a new line for the previous lap

    }


}