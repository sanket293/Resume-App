package com.sanketvagadiya.ui.Work;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sanketvagadiya.Model.WorkModel;
import com.sanketvagadiya.R;

import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment {

    private WorkViewModel workViewModel;
    private List<WorkModel> mWorkList = new ArrayList<>();
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.work_fragment, container, false);

        mContext = getContext();
        final ListView lvWorkExperience = root.findViewById(R.id.lvWorkExperience);

        workViewModel = ViewModelProviders.of(this).get(WorkViewModel.class);
        mWorkList = workViewModel.getWorkList();
        if (mWorkList.size() > 0) {
            ListAdapter adapter = new ListAdapter();
            lvWorkExperience.setAdapter(adapter);
        }

        return root;
    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mWorkList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);


            if (view == null) {
                holder = new ViewHolder();

                view = inflater.inflate(R.layout.work_listview_adapter, viewGroup, false);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvWorkAdapterRoleName = view.findViewById(R.id.tvWorkAdapterRoleName);
            holder.tvWorkAdapterCompanyName = view.findViewById(R.id.tvWorkAdapterCompanyName);
            holder.tvWorkAdapterCompanyLocation = view.findViewById(R.id.tvWorkAdapterCompanyLocation);
            holder.tvWorkAdapterJoinFrom = view.findViewById(R.id.tvWorkAdapterJoinFrom);
            holder.tvWorkAdapterJoinTo = view.findViewById(R.id.tvWorkAdapterJoinTo);
            holder.tvWorkAdapterJobResponsibilities = view.findViewById(R.id.tvWorkAdapterJobResponsibilities);


            setData(holder, i);


            return view;
        }

        private void setData(ViewHolder holder, int i) {

            holder.tvWorkAdapterRoleName.setText(mWorkList.get(i).getRoleName());
            holder.tvWorkAdapterCompanyName.setText(mWorkList.get(i).getCompanyName());
            holder.tvWorkAdapterCompanyLocation.setText(mWorkList.get(i).getCompanyLocation());
            holder.tvWorkAdapterJoinFrom.setText(mWorkList.get(i).getJoinFrom());
            holder.tvWorkAdapterJoinTo.setText(mWorkList.get(i).getJoinTo());
            holder.tvWorkAdapterJobResponsibilities.setText(mWorkList.get(i).getJobResponsibilities());

        }
    }

    public class ViewHolder {

        TextView tvWorkAdapterRoleName,
                tvWorkAdapterCompanyName,
                tvWorkAdapterCompanyLocation,
                tvWorkAdapterJoinFrom,
                tvWorkAdapterJoinTo,
                tvWorkAdapterJobResponsibilities;


    }


}
