package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.setupNoAdapter
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.extension.toRupiahs
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.Product
import com.example.mooka_customer.network.model.UMKM
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_product_populer.view.*
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
        Repository.getAllProducts().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loadinglele", it.status.toString())
                }
                Resource.SUCCESS ->{
                    view?.rv_populer?.setupNoAdapter(
                        R.layout.item_product_populer,
                        it.data!!,
                        GridLayoutManager(context, 3),
                        ::bindPopularProductRecyclerView
                    )
                    Log.d("Successlele", it.data.toString())
                }
                Resource.ERROR ->{
                    Log.d("Errorlele", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })
    }

    fun bindUmkmRecyclerView(view: View, umkm: UMKM){
        view.tv_title.text = umkm.nama
        Picasso.get().load(umkm.gambar.standard.url).into(view.bg_banner)

        view.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailUmkmFragment2(umkm.id))
        }
    }

    fun bindPopularProductRecyclerView (view: View, product: Product) {
        Picasso.get().load(product.gambar.url).into(view.iv_product_banner)
        view.tv_title_product.text = product.title
        view.tv_price_product.text = product.harga.toString().toRupiahs()
        view.tv_location_product.text = product.umkm.kota

        view.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailProdukFragment(product.id,product.umkm_id))
        }
    }
}
