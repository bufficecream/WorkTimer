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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {

    private lateinit var timerViewModel: TimerViewModel

    private val mAdapter = TimerAdapter()
    private val myItemList = arrayListOf<LapItem>()

    private var state_play: Boolean = FLAG_PLAY

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //Return the {@link Context} this fragment is currently associated with.
        sharedPref = requireContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        timerViewModel =
                ViewModelProvider(this).get(TimerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_timer, container, false)
        val textTimerView: TextView = root.findViewById(R.id.text_timer)
        timerViewModel.textTimer.observe(viewLifecycleOwner, Observer {
            textTimerView.text = it
        })

        val lapRecordRecycleView: RecyclerView = root.findViewById(R.id.lap_record)
        lapRecordRecycleView.layoutManager = LinearLayoutManager(context)
        lapRecordRecycleView.adapter = mAdapter

        //TODO test for showing
        for(i in 0..50){
            myItemList.add(LapItem("#$i", "01,01", "1,40,41", "Workout", "#666666"))
        }
        mAdapter.updateList(myItemList)

        state_play = sharedPref.getBoolean(FLAG_STR, FLAG_PLAY)

        val play_or_pauseBtn: ImageButton = root.findViewById(R.id.btn_play_or_pause)
        val resetBtn: Button = root.findViewById(R.id.resetBtn)
        val lapBtn: Button = root.findViewById(R.id.lapBtn)

        play_or_pauseBtn.setOnClickListener {

            //TODO update DB, after testing the timer
            timerViewModel.play_or_pause(state_play)

            showBtnVisibility(resetBtn)
            showBtnVisibility(lapBtn)
            updateBtnImage(play_or_pauseBtn)
        }

        resetBtn.setOnClickListener{

            timerViewModel.reset()
            sharedPref.edit().putInt(TimerFragment.tMilliSecStr, 0).apply()

            closeBtnVisibility(resetBtn)
            closeBtnVisibility(lapBtn)
            showPlayImage(play_or_pauseBtn)
        }

        lapBtn.setOnClickListener{

            //TODO update DB, after testing the timer
//            timerViewModel.lap()
        }

        return root
    }

    private fun showBtnVisibility(btn: Button) {

        if (btn.visibility == View.GONE) btn.visibility = View.VISIBLE
    }

    private fun closeBtnVisibility(btn: Button){

        if (btn.visibility == View.VISIBLE) btn.visibility = View.GONE
    }

    private fun updateBtnImage(btn: ImageButton){

        if(state_play){

            btn.setImageResource(android.R.drawable.ic_media_pause)
        }else{

            btn.setImageResource(android.R.drawable.ic_media_play)
        }

        updateFlag(state_play)
    }

    private fun updateFlag(flag: Boolean) {

//        flag = if (flag) FLAG_PAUSE else FLAG_PLAY
//        return if(flag) FLAG_PAUSE else FLAG_PLAY

        state_play = if(flag) FLAG_PAUSE else FLAG_PLAY
        sharedPref.edit().putBoolean(FLAG_STR, state_play).apply()
    }

    private fun showPlayImage(btn: ImageButton){

        if(!state_play){

            btn.setImageResource(android.R.drawable.ic_media_play)
            updateFlag(state_play)
        }
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


