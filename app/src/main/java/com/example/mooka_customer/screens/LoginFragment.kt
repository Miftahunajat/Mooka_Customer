package com.example.mooka_customer.screens


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooka_customer.R
import com.example.mooka_customer.network.Repository
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_register.view.btn_login

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.tv_masuk_disini.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment2())
        }
        view.btn_login.setOnClickListener {
            loginUser(et_no_telp.text.toString(), et_password.text.toString())
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun loginUser(noTelp:String, password:String) {
        Repository.getAllUsers().observe(this, Observer {

        })
    }

}
