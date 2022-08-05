package com.example.workingtimer.ui.commonWorks

import android.app.AlertDialog
import android.app.Dialog
//import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.workingtimer.R

import androidx.fragment.app.DialogFragment

class AddNewWorkDialog() : DialogFragment() {

    val TAG: String = "AddNewWorkDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_new_work, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener {

        }

        val colorBtn: Button = view.findViewById(R.id.color_button)
        colorBtn.setOnClickListener {
            //TODO pop up a color picker dialog
        }

//        val posBtn: Button = view.findViewById()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            with(builder){
                setView(R.layout.dialog_add_new_work)
                setPositiveButton(R.string.confirm) { dialog, id ->
                    //TODO store the data into DB
                }
                setNegativeButton(R.string.cancel) { dialog, id ->
                    //TODO just leave
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }



}