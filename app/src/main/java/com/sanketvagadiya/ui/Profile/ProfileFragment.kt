package com.sanketvagadiya.ui.Profile

import androidx.lifecycle.ViewModelProviders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sanketvagadiya.Model.WorkModel
import com.sanketvagadiya.R
import com.sanketvagadiya.ui.Work.WorkFragment

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private var profileViewModel: ProfileViewModel? = null

    private val mContext: Context? = null
    private var mQueue: RequestQueue? = null
    private var jsonObject = JSONObject()
    private val lvWorkExperience: ListView? = null
    private var tvProfileName: TextView? = null
    private var tvProfileSummary: TextView? = null
    private var ivProfileImage: ImageView? = null
    private var ivLinkedIn: ImageView? = null
    private var ivPlayStore: ImageView? = null
    private var ivGithub: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.profile_fragment, container, false)

        tvProfileName = root.findViewById(R.id.tvProfileName)
        tvProfileSummary = root.findViewById(R.id.tvProfileSummary)
        ivProfileImage = root.findViewById(R.id.ivProfileImage)

        ivLinkedIn = root.findViewById(R.id.ivLinkedIn)
        ivPlayStore = root.findViewById(R.id.ivPlayStore)
        ivGithub = root.findViewById(R.id.ivGithub)

        ivLinkedIn!!.setOnClickListener { goToUrl(Constants.LINKEDIN_URL) }
        ivPlayStore!!.setOnClickListener { goToUrl(Constants.PLAYSTORE_URL) }
        ivGithub!!.setOnClickListener { goToUrl(Constants.GITHUB_URL) }

        return root
    }

    private fun goToUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mQueue = Volley.newRequestQueue(context!!)
        profileDatafromJson()

    }


    private fun profileDatafromJson() {
        val request = JsonObjectRequest(Request.Method.GET, Constants.PROFILE_URL, null, Response.Listener { response -> jsonObject = response }, Response.ErrorListener { error -> Log.e("err", "err$error") })


        mQueue!!.addRequestFinishedListener(
                RequestQueue.RequestFinishedListener<Any> {
                    try {
                        val `object` = jsonObject.getJSONObject(Constants.PROFILE_JSON_OBJECT)
                        val profileName = `object`.getString(Constants.PROFILE_JSON_PROFILE_NAME)
                        val imageUrl = `object`.getString(Constants.PROFILE_JSON_IMAGEURL)
                        val profileSummary = `object`.getString(Constants.PROFILE_JSON_PROFILE_SUMMARY)


                        tvProfileName!!.text = profileName
                        tvProfileSummary!!.text = profileSummary

                        Glide.with(context!!)
                                .load(imageUrl)
                                .apply(RequestOptions.circleCropTransform())
                                .into(ivProfileImage!!)


                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
        )
        mQueue!!.add(request)
    }

}
