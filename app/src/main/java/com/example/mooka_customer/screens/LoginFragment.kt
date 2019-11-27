package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.*
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
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
        view.tv_daftar_disini.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment2())
        }
        view.btn_login.setOnClickListener {
            view.btn_login.finishLoading()
            loginUser(et_no_telp.text.toString(), et_password.text.toString(), it)
        }
        if (context!!.getPrefInt("user_id") != -1){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun loginUser(noTelp:String, password:String, view: View) {
        Repository.getAllUsers().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    val user: User? = it.data!!.find { user ->  noTelp == user.no_telfon && password == user.password}
                    if (user == null)
                        context!!.showAlertDialog("Gagal Login!", "Pastikan nomor telepon dan password Anda telah terdaftar")
                    else {
                        context!!.savePref("user_id", user.id)
                        view.btn_login.finishLoading()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
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
