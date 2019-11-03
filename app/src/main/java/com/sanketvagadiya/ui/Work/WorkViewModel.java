package com.sanketvagadiya.ui.Work;

import androidx.lifecycle.ViewModel;

import com.sanketvagadiya.Model.WorkModel;

import java.util.ArrayList;
import java.util.List;

public class WorkViewModel extends ViewModel {

    private List<WorkModel> mWorkList;

    public WorkViewModel() {
        mWorkList = new ArrayList<>();
        getWorkData();

    }

    private void getWorkData() {
        //todo create json method to get data from internet
        mWorkList.add(new WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"));

        mWorkList.add(new WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"));
        mWorkList.add(new WorkModel("Android Developer",
                "Cygnus Softech", "Surat", "June 2016", "June 2017", "jlkgdsjlgdjs \n jlksahldsgs\nilsuriuweiquir\n ewthujeqwgjsyfga \nehwjytrweyajdgasjdgh", "sss"));

    }

    public List<WorkModel> getWorkList() {
        return mWorkList;
    }
}
