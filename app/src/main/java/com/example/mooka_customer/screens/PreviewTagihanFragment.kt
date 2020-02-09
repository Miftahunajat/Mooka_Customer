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
import com.example.mooka_customer.extension.*
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.DataCekPembayaran
import kotlinx.android.synthetic.main.fragment_preview_tagihan.view.*
import kotlinx.android.synthetic.main.item_tagihans.view.*
import kotlinx.android.synthetic.main.item_tagihans.view.tv_nomor_briva

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
        val previewTagihanFragmentArgs by navArgs<PreviewTagihanFragmentArgs>()
        getCustCode(view, previewTagihanFragmentArgs.custcode)
        return view
    }

    private fun getCustCode(view: View, custcode: String) {
        Repository.cekPembayaran(custcode).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.e("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    setData(view!!, it.data!!.data)
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

    private fun setData(view: View, data: DataCekPembayaran) {
        view.tv_nomor_briva.text = "${data.BrivaNo}${data.CustCode}"
        view.tv_harga_briva2.text = data.Amount.toString().toRupiahs()
        view.tv_tanggal2.text = "Sebelum tanggaal ${data.expiredDate}"
        if (data.statusBayar.equals("N")){
            view.tv_status.toBelumMembayar()
        }else{
            view.tv_status.toSudahMembayar()
        }
    }
}
