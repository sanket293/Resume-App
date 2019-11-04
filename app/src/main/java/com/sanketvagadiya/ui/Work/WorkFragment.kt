package com.sanketvagadiya.ui.Work

import androidx.lifecycle.ViewModelProviders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.getSystemService

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sanketvagadiya.Constants.Constants
import com.sanketvagadiya.Model.ProjectModel
import com.sanketvagadiya.Model.WorkModel
import com.sanketvagadiya.R
import com.sanketvagadiya.ui.Projects.ProjectsFragment

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class WorkFragment : Fragment() {

    private val workViewModel: WorkViewModel? = null
    private val mWorkList = ArrayList<WorkModel>()
    private var mContext: Context? = null
    private var mQueue: RequestQueue? = null
    private var jsonObject = JSONObject()
    private var lvWorkExperience: ListView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.work_fragment, container, false)

        mContext = context
        lvWorkExperience = root.findViewById(R.id.lvWorkExperience)

        //        workViewModel = ViewModelProviders.of(this).get(WorkViewModel.class);
        //        mWorkList = workViewModel.getWorkList();

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mQueue = Volley.newRequestQueue(context!!)
        sendWorkListJson()

    }

    private fun sendWorkListJson() {
        val request = JsonObjectRequest(Request.Method.GET, Constants.PAST_WORK_URL, null, Response.Listener { response -> jsonObject = response }, Response.ErrorListener { error -> Log.e("err", "err$error") })


        mQueue!!.addRequestFinishedListener(
                RequestQueue.RequestFinishedListener<Any> {
                    try {
                        val jsonArray = jsonObject.getJSONArray(Constants.PAST_WORK_JSONARRAY)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val roleName = jsonObject.getString(Constants.PAST_WORK_JSON_ROLE_NAME)
                            val companyName = jsonObject.getString(Constants.PAST_WORK_JSON_COMPANY_NAME)
                            val companyLocation = jsonObject.getString(Constants.PAST_WORK_JSON_LOCATION)
                            val joinFrom = jsonObject.getString(Constants.PAST_WORK_JSON_JOIN_FROM)
                            val joinTo = jsonObject.getString(Constants.PAST_WORK_JSON_JOIN_TO)
                            val jobResponsibilities = jsonObject.getString(Constants.PAST_WORK_JSON_RESPONSIBILITY)

                            val imageUrl = jsonObject.getString(Constants.PAST_WORK_JSON_IMAGEURL)

                            mWorkList.add(WorkModel(roleName, companyName, companyLocation, joinFrom, joinTo, jobResponsibilities, imageUrl
                            ))
                        }

                        if (mWorkList.size > 0) {
                            val adapter = ListAdapter()
                            lvWorkExperience!!.adapter = adapter
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
        )
        mQueue!!.add(request)
    }


    inner class ListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return mWorkList.size
        }

        override fun getItem(i: Int): Any {
            return i
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            val holder: ViewHolder

//            val inflater = mContext!!.getSystemService(mContext!!.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val inflater = LayoutInflater.from(getActivity());

            if (view == null) {
                holder = ViewHolder()

                view = inflater.inflate(R.layout.work_listview_adapter, viewGroup, false)

                view!!.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }

            holder.tvWorkAdapterRoleName = view.findViewById(R.id.tvWorkAdapterRoleName)
            holder.tvWorkAdapterCompanyName = view.findViewById(R.id.tvWorkAdapterCompanyName)
            holder.tvWorkAdapterCompanyLocation = view.findViewById(R.id.tvWorkAdapterCompanyLocation)
            holder.tvWorkAdapterJoinFrom = view.findViewById(R.id.tvWorkAdapterJoinFrom)
            holder.tvWorkAdapterJoinTo = view.findViewById(R.id.tvWorkAdapterJoinTo)
            holder.tvWorkAdapterJobResponsibilities = view.findViewById(R.id.tvWorkAdapterJobResponsibilities)
            holder.ivCompanyImage = view.findViewById<View>(R.id.ivCompanyImage) as ImageView

            setData(holder, i)


            return view
        }

        private fun setData(holder: ViewHolder, i: Int) {

            holder.tvWorkAdapterRoleName!!.text = mWorkList[i].roleName
            holder.tvWorkAdapterCompanyName!!.text = mWorkList[i].companyName
            holder.tvWorkAdapterCompanyLocation!!.text = mWorkList[i].companyLocation
            holder.tvWorkAdapterJoinFrom!!.text = mWorkList[i].joinFrom
            holder.tvWorkAdapterJoinTo!!.text = mWorkList[i].joinTo
            holder.tvWorkAdapterJobResponsibilities!!.text = mWorkList[i].jobResponsibilities

            Glide.with(context!!)
                    .load(mWorkList[i].imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.ivCompanyImage!!)

        }
    }

    inner class ViewHolder {

        internal var tvWorkAdapterRoleName: TextView? = null
        internal var tvWorkAdapterCompanyName: TextView? = null
        internal var tvWorkAdapterCompanyLocation: TextView? = null
        internal var tvWorkAdapterJoinFrom: TextView? = null
        internal var tvWorkAdapterJoinTo: TextView? = null
        internal var tvWorkAdapterJobResponsibilities: TextView? = null
        internal var ivCompanyImage: ImageView? = null

    }


}
