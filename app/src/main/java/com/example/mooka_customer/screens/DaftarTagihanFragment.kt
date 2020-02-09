package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.*
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import kotlinx.android.synthetic.main.fragment_daftar_tagihan.view.*
import kotlinx.android.synthetic.main.item_tagihans.view.*

/**
 * A simple [Fragment] subclass.
 */
class DaftarTagihanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daftar_tagihan, container, false)
        getTagihans(view)
        // Inflate the layout for this fragment
        return view
    }

    private fun getTagihans(view: View) {
        val userid = context?.getPrefInt("userid")
        Repository.getAllTagihans(userid!!).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    view.rv_tagihans.setupNoAdapter(R.layout.item_tagihans, it.data!!){view,tagihan->
                        view.tv_nomor_briva.text = tagihan.number.toString()
                        view.tv_harga_briva.text = tagihan.jumlah.toString()
                        view.tv_tanggal.text = tagihan.created_at
                        if (tagihan.status == "N"){
                            view.tv_status.toBelumMembayar()
                        }else{
                            view.tv_status.toSudahMembayar()
                        }
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
