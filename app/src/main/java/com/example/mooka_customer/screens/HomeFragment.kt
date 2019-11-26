package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.setupNoAdapter
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.UMKM
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_product_rekomendasi.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setupRekomendasi(view)
        setupPopuler(view)
        // Inflate the layout for this fragment
        return view
    }



    private fun setupRekomendasi(view: View?) {
        Repository.getAllUmkms().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    view?.rv_rekomendasi?.setupNoAdapter(
                        R.layout.item_product_rekomendasi,
                        it.data!!,
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                        ::bindUmkmRecyclerView
                    )
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

    private fun setupPopuler(view: View?) {
        view!!.rv_populer.setupNoAdapter(
            R.layout.item_product_populer,
            listOf("1","2","3","4","5"),
            GridLayoutManager(context, 3)
        ) { _, _->

        }
    }

    fun bindUmkmRecyclerView(view: View, umkm: UMKM){
        view.tv_title.text = umkm.nama
//        view?.tv_subtitle.setText()
        Picasso.get().load(umkm.gambar.url).into(view.bg_banner)
    }
}
