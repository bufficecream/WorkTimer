package com.example.workingtimer.ui.timer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R
import com.example.workingtimer.ui.commonWorks.CommonWorksItem

class TimerAdapter: RecyclerView.Adapter<TimerAdapter.mViewHolder>() {

    var unAssignList = listOf<LapItem>()

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val _id: TextView = itemView.findViewById(R.id.text_number)
        private val lapTime: TextView = itemView.findViewById(R.id.text_lap_time)
        private val totalTime: TextView = itemView.findViewById(R.id.text_total_time)
        private val workName: TextView = itemView.findViewById(R.id.text_work_name)
        private val color: TextView = itemView.findViewById(R.id.color_name)

        fun bind(item: LapItem){

            _id.text = item.id
            lapTime.text = item.lapTime
            totalTime.text = item.totalTime
            workName.text = item.workName

            //show the color in the point by setting a background first then cover it
            var tint:Int = Color.parseColor(item.color)
            color.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(tint, BlendModeCompat.SRC_IN)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //TODO change Item style
        val itemView = inflater.inflate(R.layout.item_lap_record, parent, false)

        return mViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(unAssignList[position])
    }

    override fun getItemCount(): Int {
        return unAssignList.size
    }

    fun updateList(list:ArrayList<LapItem>){
        unAssignList = list
    }


}