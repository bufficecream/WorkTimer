package com.example.workingtimer.ui.commonWorks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R

class CommonWorksAdapter: RecyclerView.Adapter<CommonWorksAdapter.ViewHolder>() {

    private var mUnAssignList = listOf<CommonWorksItem>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val workName: TextView = itemView.findViewById(R.id.work_name)
        private val colorName: TextView = itemView.findViewById(R.id.color_name)

        fun bind(item: CommonWorksItem){
            workName.text = item.nameWork

            val tint:Int = Color.parseColor(item.nameColor)
            colorName.background.setTint(tint)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_common_works, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUnAssignList[position])
    }

    override fun getItemCount(): Int {
        return mUnAssignList.size
    }

    fun updateList(list: ArrayList<CommonWorksItem>){
        mUnAssignList = list
    }

}