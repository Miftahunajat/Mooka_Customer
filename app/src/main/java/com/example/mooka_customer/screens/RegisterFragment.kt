package com.example.mooka_customer.screens


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.btn_login
import kotlinx.android.synthetic.main.fragment_register.view.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.tv_masuk_disini.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        view.btn_daftar.setOnClickListener {
            if (isValid()) {
                context!!.showmessage("Pastikan semua field terisi")

            } else {
                Repository.register(view!!.et_name_register.text.toString(), view.et_email_register.text.toString(), view.et_no_telp_register.text.toString(), view.et_password_register.text.toString())
                    .observe(this, Observer {
                        when(it?.status){
                            Resource.LOADING ->{
                                Log.d("Loading", it.status.toString())
                            }
                            Resource.SUCCESS ->{
                                context!!.showmessage("Anda berhasil register")
                                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMainActivity())
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

        // Inflate the layout for this fragment
        return view
    }

    fun isValid()  :Boolean {
        return view!!.et_name_register.text.toString().isEmpty() || view!!.et_email_register.text.toString().isEmpty() ||
                view!!.et_no_telp_register.text.toString().isEmpty() || view!!.et_password_register.text.toString().isEmpty()
    }


}
