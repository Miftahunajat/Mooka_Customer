package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.clearPref
import com.example.mooka_customer.extension.getPrefInt
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.extension.toRupiahs
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupUser(view!!)

        view.iv_logout_arrow.setOnClickListener {
            context!!.clearPref()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginActivity())
        }

        view.imageView13.setOnClickListener {
            context!!.clearPref()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginActivity())
        }

        view.textView12.setOnClickListener {
            context!!.clearPref()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginActivity())
        }

        return view
    }

    private fun setupUser(view: View) {
        Repository.getAllUsers().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    var idUser = context!!.getPrefInt("user_id")
                    val user = it.data?.find { user ->  user.id == idUser}
                    view.tvNamaUser.text = user?.nama
                    view.tvEmailUser.text = user?.email
                    view.tv_total_donasi.text = user?.total_donasi?.toRupiahs()

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
