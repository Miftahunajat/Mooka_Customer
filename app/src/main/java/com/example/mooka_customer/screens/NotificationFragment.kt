package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.getPrefInt
import com.example.mooka_customer.extension.setupNoAdapter
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.item_notification.view.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        getAllNotificationss(view!!)
        // Inflate the layout for this fragment
        return view
    }

    private fun getAllNotificationss(view: View) {
        val user_id = context?.getPrefInt("user_id")
        Repository.getAllNotifications(user_id!!).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    rvNotification.setupNoAdapter(
                        R.layout.item_notification,
                        it.data!!
                    ){v,n->
                        v.tvJudulNotification.text = n.judul
                        v.tvDeskripsiNotification.text = n.text
                    }
                    Log.d("Success", it.data.toString())
                }
                Resource.ERROR ->{
                    Log.d("Error", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })
    }


}
