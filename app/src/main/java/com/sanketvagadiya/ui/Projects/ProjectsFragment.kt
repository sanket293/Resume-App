package com.sanketvagadiya.ui.Projects

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import android.widget.Toast

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
import com.sanketvagadiya.R

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class ProjectsFragment : Fragment() {

    private val mViewModel: ProjectsViewModel? = null
    private var mContext: Context? = null
    private val projectList = ArrayList<ProjectModel>()
    private var mQueue: RequestQueue? = null
    private var jsonObject = JSONObject()
    private var adapter: ListAdapter? = null
    private var lvProjectList: ListView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mContext = activity

        val root = inflater.inflate(R.layout.projects_fragment, container, false)

        lvProjectList = root.findViewById(R.id.lvProjectList)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mQueue = Volley.newRequestQueue(context!!)
        sendProjectListJson()

    }


    fun sendProjectListJson() {
        val request = JsonObjectRequest(Request.Method.GET, Constants.PROJECT_URL, null, Response.Listener { response -> jsonObject = response }, Response.ErrorListener { error -> Log.e("err", "err$error") })


        mQueue!!.addRequestFinishedListener(
                RequestQueue.RequestFinishedListener<Any> {
                    try {
                        val jsonArray = jsonObject.getJSONArray(Constants.PROJECT_JSONARRAY)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val projectName = jsonObject.getString(Constants.PROJECT_JSON_PROJECTNAME)
                            val imageUrl = jsonObject.getString(Constants.PROJECT_JSON_IMAGEURL)
                            val projectUrl = jsonObject.getString(Constants.PROJECT_JSON_PROJECTURL)
                            projectList.add(ProjectModel(projectName, imageUrl, projectUrl))
                        }
                        if (projectList.size > 0) {
                            adapter = ListAdapter()
                            lvProjectList!!.adapter = adapter
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
            return projectList.size
        }

        override fun getItem(i: Int): Any {
            return i
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
            var convertView = convertView

            val holder: ViewHolder

//            val inflater = mContext!!.getSystemService(mContext!!.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val inflater = LayoutInflater.from(getActivity());


            if (convertView == null) {
                holder = ViewHolder()

                convertView = inflater.inflate(R.layout.project_listview_adapter, viewGroup, false)

                holder.tvProjectName = convertView!!.findViewById<View>(R.id.tvProjectName) as TextView
                holder.ivProjectImage = convertView.findViewById<View>(R.id.ivProjectImage) as ImageView

                holder.tvProjectName!!.text = projectList[i].projectName

                Glide.with(context!!)
                        .load(projectList[i].imageUrl)
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.ivProjectImage!!)


                convertView.setOnClickListener {
                    val uri = Uri.parse(projectList[i].projectUrl) // missing 'http://' will cause crashed
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }


                convertView.tag = holder

            } else {
                holder = convertView.tag as ViewHolder
            }

            return convertView

        }
    }

    inner class ViewHolder {
        internal var tvProjectName: TextView? = null
        internal var ivProjectImage: ImageView? = null
    }


}
