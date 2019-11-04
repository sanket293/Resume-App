package com.sanketvagadiya.ui.Work

import androidx.lifecycle.ViewModel

import com.sanketvagadiya.Model.WorkModel

import java.util.ArrayList

class WorkViewModel : ViewModel() {

    private val mWorkList: MutableList<WorkModel>

    val workList: List<WorkModel>
        get() = mWorkList

    init {
        mWorkList = ArrayList()
        getWorkData()

    }

    private fun getWorkData() {
        //todo create json method to get data from internet
        mWorkList.add(WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"))

        mWorkList.add(WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"))
        mWorkList.add(WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"))

    }
}
