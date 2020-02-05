package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.getPrefSetString
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource

/**
 * A simple [Fragment] subclass.
 */
class PreviewTagihanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view =inflater.inflate(R.layout.fragment_preview_tagihan, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()

        val custcodes = context!!.getPrefSetString("custcode")
        Log.e("jumlah custcode", custcodes!!.size.toString())
        custcodes.forEach {
            Repository.cekPembayaran(it).observe(this, Observer {
                when(it?.status){
                    Resource.LOADING ->{
                        Log.e("Loading", it.status.toString())
                    }
                    Resource.SUCCESS ->{
                        Log.e("Success", it.data.toString())
                        Log.e("custcode", it.data.toString())
                        //20123456789, 18123456789, 21123456789, 19123456789, 22123456789
                    }
                    Resource.ERROR ->{
                        Log.e("Error", it.toString())

                        context?.showmessage("Something is wrong")
                    }
                }
            })
        }

//        val previewTagihanFragmentArgs by navArgs<PreviewTagihanFragmentArgs>()
//        Repository.cekPembayaran(previewTagihanFragmentArgs.custcode).observe(this, Observer {
//            when(it?.status){
//                Resource.LOADING ->{
//                    Log.d("Loading", it.status.toString())
//                }
//                Resource.SUCCESS ->{
//                    Log.e("previewtagihan", it.data.toString())
//
//                    Log.d("Success", it.data.toString())
//                }
//                Resource.ERROR ->{
//                    Log.d("Error", it.message!!)
//                    context?.showmessage("Something is wrong")
//                }
//            }
//        })
    }
}
