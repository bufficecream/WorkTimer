package com.example.workingtimer.ui.commonWorks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingtimer.R
import com.example.workingtimer.ui.timer.LapItem
import com.example.workingtimer.ui.timer.TimerAdapter

class CommonWorksFragment : Fragment() {

    private lateinit var commonWorksViewModel: CommonWorksViewModel

    private val mAdapter = CommonWorksAdapter()
    private val myItemList = arrayListOf<CommonWorksItem>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        commonWorksViewModel =
                ViewModelProvider(this).get(CommonWorksViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_common_works, container, false)

        val commonWorksListRecycleView: RecyclerView = root.findViewById(R.id.commonWorksList)
        commonWorksListRecycleView.layoutManager = LinearLayoutManager(context)
        commonWorksListRecycleView.adapter = mAdapter

        val addNewWorkBtn: Button = root.findViewById(R.id.addNewBtn)
        addNewWorkBtn.setOnClickListener {
            //TODO implement pop-up a dialog
            val newFragment = AddNewWorkDialog()
            newFragment.show(childFragmentManager, newFragment.TAG)
        }

        // TODO this is for the demo level, we should provide proper interface for next
        testShowing()

        return root
    }

    private fun testShowing(){
        for(i in 0..50){
            myItemList.add(CommonWorksItem("Email", "#666666"))
        }
        mAdapter.updateList(myItemList)
    }
}