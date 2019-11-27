package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.setupNoAdapter
import com.example.mooka_customer.extension.showmessage
import com.example.mooka_customer.extension.toRupiahs
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_produk.view.*

/**
 * A simple [Fragment] subclass.
 */
class DetailProdukFragment : Fragment() {


    var productId = 0
    var umkmId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_produk, container, false)
        val args by navArgs<DetailProdukFragmentArgs>()

        productId = args.idProduct
        umkmId = args.idUmkm

        setupProductDetail(view!!)
        setupProductTerkait(view)
        // Inflate the layout for this fragment
        return view
    }

    private fun setupProductTerkait(view: View) {
        Repository.getAllProducts().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    val filteredData = it.data?.filter { it.umkm_id == umkmId }
                    view.rv_produk_lainnnya.setupNoAdapter(
                        R.layout.item_pilihan_toko_lainnya,
                        filteredData!!,
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                        ::bindPilihanTokoLainnya
                    )
                    Log.d("Success", it.data.toString())
                }
                Resource.ERROR ->{
                    Log.d("Error", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })
    }

    private fun setupProductDetail(view: View) {
        Repository.getProductDetail(productId).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    Picasso.get().load(it.data?.gambar?.url).into(view.iv_banner)
                    view.tv_title.text = it.data?.title
                    view.tv_price.text = it.data?.harga.toString().toRupiahs()

                    view.tv_title_alamat.text = it.data?.umkm?.nama
                    view.tv_subtitle_alamat.text = it.data?.umkm?.alamat
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

fun bindPilihanTokoLainnya(view: View, product: Product) {

}
