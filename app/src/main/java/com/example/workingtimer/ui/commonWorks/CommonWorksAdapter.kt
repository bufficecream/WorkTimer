package com.example.workingtimer.ui.commonWorks

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R

class CommonWorksAdapter: RecyclerView.Adapter<CommonWorksAdapter.mViewHolder>() {

    var unAssignList = listOf<CommonWorksItem>()

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val workName: TextView = itemView.findViewById(R.id.work_name)
        private val colorName: TextView = itemView.findViewById(R.id.color_name)

        fun bind(item: CommonWorksItem){

            workName.text = item.nameWork

            //show the color in the point by setting a background first then cover it
            var tint:Int = Color.parseColor(item.nameColor)
            colorName.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(tint, BlendModeCompat.SRC_IN)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_common_works, parent, false)

        return mViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        holder.bind(unAssignList[position])
    }

    override fun getItemCount(): Int {
        return unAssignList.size
    }

    fun updateList(list:ArrayList<CommonWorksItem>){
        unAssignList = list
    }

}