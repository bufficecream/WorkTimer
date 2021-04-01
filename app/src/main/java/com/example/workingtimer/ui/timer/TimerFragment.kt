package com.example.workingtimer.ui.timer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R

class TimerFragment : Fragment() {

    private lateinit var timerViewModel: TimerViewModel

    private val mAdapter = TimerAdapter()
    private val myItemList = arrayListOf<LapItem>()

    private var btn_state_play: Boolean = FLAG_PLAY

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")

        //Return the {@link Context} this fragment is currently associated with.
        sharedPref = requireContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        timerViewModel =
                ViewModelProvider(this).get(TimerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_timer, container, false)
        val textTimerView: TextView = root.findViewById(R.id.text_timer)
        timerViewModel.textTimer.observe(viewLifecycleOwner, Observer {

            //TODO change to the counting time when the user swipe to the other page
            textTimerView.text = it
        })

        val lapRecordRecycleView: RecyclerView = root.findViewById(R.id.lap_record)
        lapRecordRecycleView.layoutManager = LinearLayoutManager(context)
        lapRecordRecycleView.adapter = mAdapter
        //TODO stealth it when the showing function is complete
        testShowing(mAdapter)



        timerViewModel.tMilliSec = sharedPref.getInt(tMilliSecStr, 0)

        btn_state_play = sharedPref.getBoolean(FLAG_STR, FLAG_PLAY)

        val play_or_pauseBtn: ImageButton = root.findViewById(R.id.btn_play_or_pause)
        val resetBtn: Button = root.findViewById(R.id.resetBtn)
        val lapBtn: Button = root.findViewById(R.id.lapBtn)

        play_or_pauseBtn.setOnClickListener {

            //TODO update DB, after testing the timer
            timerViewModel.play_or_pause(btn_state_play)

            showBtnVisibility(resetBtn)
            showBtnVisibility(lapBtn)
            updateBtnImage(play_or_pauseBtn)
        }

        resetBtn.setOnClickListener{

            timerViewModel.reset()
            sharedPref.edit().putInt(tMilliSecStr, 0).apply()

            closeBtnVisibility(resetBtn)
            closeBtnVisibility(lapBtn)
            showPlayImage(play_or_pauseBtn)
        }

        lapBtn.setOnClickListener{

            //TODO update DB, after testing the timer
//            timerViewModel.lap()
        }

        restoreTimerState(resetBtn, lapBtn, play_or_pauseBtn)

        return root
    }

    private fun restoreTimerState(resetBtn: Button, lapBtn: Button, play_or_pauseBtn: ImageButton){

        timerViewModel.updateTime(timerViewModel.tMilliSec / 1000)
        if(timerViewModel.tMilliSec > 0){

            showBtnVisibility(resetBtn)
            showBtnVisibility(lapBtn)
        }

        if(!btn_state_play){

            timerViewModel.play()
            play_or_pauseBtn.setImageResource(android.R.drawable.ic_media_pause)

        }
    }

    private fun testShowing(mAdapter: TimerAdapter){

        for(i in 0..50){
            myItemList.add(LapItem("#$i", "01:01:01", "09:40:41", "Workout", "#666666"))
        }
        mAdapter.updateList(myItemList)
    }

    private fun showBtnVisibility(btn: Button) {

        if (btn.visibility == View.GONE) btn.visibility = View.VISIBLE
    }

    private fun closeBtnVisibility(btn: Button){

        if (btn.visibility == View.VISIBLE) btn.visibility = View.GONE
    }

    private fun updateBtnImage(btn: ImageButton){

        if(btn_state_play){

            btn.setImageResource(android.R.drawable.ic_media_pause)
        }else{

            btn.setImageResource(android.R.drawable.ic_media_play)
        }

        updateFlag(btn_state_play)
    }

    private fun updateFlag(flag: Boolean) {

        btn_state_play = if(flag) FLAG_PAUSE else FLAG_PLAY
        sharedPref.edit().putBoolean(FLAG_STR, btn_state_play).apply()
    }

    private fun showPlayImage(btn: ImageButton){

        if(!btn_state_play){

            btn.setImageResource(android.R.drawable.ic_media_play)
            updateFlag(btn_state_play)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

        sharedPref.edit().putInt(tMilliSecStr, timerViewModel.tMilliSec).apply()

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")

//        if(!btn_state_play){
//
//            timerViewModel.cancelTicker()
//            timerViewModel.playBackground()
//        }
        //TODO light up a service to keep running the timer

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    companion object {

        const val TAG: String = "TimerFragment"

        const val tMilliSecStr = "tMilliSec"
        const val FLAG_STR = "state_play"
        const val FLAG_PLAY = true
        const val FLAG_PAUSE = false

        const val TIME_ORIGIN = "00:00:00"
    }


}


