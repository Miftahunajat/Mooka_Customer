package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_umkm.view.*

/**
 * A simple [Fragment] subclass.
 */
class DetailUmkmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_umkm, container, false)
        getUmkmDetail(view)
        return  view
    }

    private fun getUmkmDetail (view: View) {
        val detailUmkmArgs by navArgs<DetailUmkmFragmentArgs>()

        Repository.getUmkmDetail(detailUmkmArgs.idUmkm).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    Picasso.get().load(it.data!!.gambar.url).into(view.iv_store_banner)
                    view.tv_title_umkm.text = it.data!!.nama
//                    view.tv_jenis_umkm.text = it.data!!.jenis_umkm.tipe_sum
                    view.tv_store_user.text = it.data!!.nama_pemilik
                    view.tv_store_address.text = it.data!!.alamat

                    Log.d("Success", it.data.toString())
                }
                Resource.ERROR ->{
                    Log.e("Error", it.message!!)
                    Log.e("data", it.data.toString())
                    context?.showmessage("Something is wrong")
                }
            }
        })
    }

}
