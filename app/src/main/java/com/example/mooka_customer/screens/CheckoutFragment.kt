package com.example.mooka_customer.screens


import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.*
import com.example.mooka_customer.network.Repository
import com.example.mooka_customer.network.lib.Resource
import com.example.mooka_customer.network.model.Cart
import com.example.mooka_customer.network.model.JenisPengiriman
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import kotlinx.android.synthetic.main.item_barang_checkout.view.*

/**
 * A simple [Fragment] subclass.
 */
class CheckoutFragment : Fragment() {

    var subTotalPrice = 0
    var deliveryPrice = 0
    var totalPrice = 0
    var additionalDonation = 0

    var selectedIdJenisPengiriman:Int = 0

    var jenisPengirimanList :List<JenisPengiriman> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        jenisPengirimanList = getJenisPengirimanData()
        getAllCarts()

        view.iv_address_form.setOnClickListener {
            context!!.showEditableBottomSheetDialog("Form Alamat", "Masukkan alamat Anda", InputType.TYPE_CLASS_TEXT ){
                view.tv_address_pengiriman.text = it
            }
        }

        view.iv_select_jenis_pengiriman.setOnClickListener {
            context!!.showListDialog(
                jenisPengirimanList,
                context!!
            ) {
                deliveryPrice = it.harga
                totalPrice = deliveryPrice + subTotalPrice

                selectedIdJenisPengiriman = it.id

                view.tv_nama_jasa_pengiriman.text = it.nama
                view.tv_biaya_pengiriman.text = it.harga.toString().toRupiahs()
                view.tv_subtotal_pengiriman.text = it.harga.toString().toRupiahs()
                view.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
            }
        }

        view.checkbox_donasi.addEventDialogListener {
            if (it.isChecked) {
                totalPrice += calculateDonation()

                view.tv_pembulatan_donasi.text = "Pembulatan Donasi sebesar " +  calculateDonation()
                view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()

                view!!.constraintLayoutDonasiTambahan.visibility = View.VISIBLE
            } else {
                totalPrice-= calculateDonation()

                view.tv_pembulatan_donasi.text = " "
                view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()

                view!!.constraintLayoutDonasiTambahan.visibility = View.GONE
            }
        }

        view.checkBox_tambah_donasi.addEventDialogListener {
            if (!it.isChecked) {
                totalPrice -= additionalDonation
                additionalDonation = 0

                view!!.tv_donasi_tambahan.text = " "
                view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
            } else {
                context!!.showEditableBottomSheetDialog("Donasi tamabahn", "Jumlah donasi tambahan", InputType.TYPE_CLASS_NUMBER ){
                    additionalDonation += it.toInt()

                    totalPrice += additionalDonation

                    view!!.tv_donasi_tambahan.text = "Donasi tambahan " + it
                    view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
                }
            }
        }

        view.btn_bayar_checkout.setOnClickListener {
            if (view.tv_address_pengiriman.text.toString().isEmpty() || view.tv_biaya_pengiriman.text.toString().isEmpty()) {
                context?.showAlertDialog("Warning", "Silahkan isi alamat dan pilih jasa pengiriman!")
            } else {

                it.toLoading()

                val id = context?.getPrefInt("user_id")

                Repository.checkout(id!!.toString(), selectedIdJenisPengiriman , calculateDonation()).observe(this, Observer {
                    when(it?.status){
                        Resource.LOADING ->{
                            Log.d("Loading", it.status.toString())
                        }
                        Resource.SUCCESS ->{
                            context!!.showAlertDialog("Checkout Berhasil",
                                "Selamat, barang yang Anda yang ada dikeranjang berhasil dibeli...") {
                                findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment())
                            }
                            Log.d("Success checkout", it.data.toString())
                        }
                        Resource.ERROR ->{

                            if (it.status == 92) {
                                context?.showmessage("Maaf saldo Anda tidak cukup")
                            } else {
                                context?.showmessage("Something is wrong" + it.status)
                            }
                            Log.d("Error", it.message!!)
                        }
                    }
                })

                it.finishLoading()
            }
        }

        return view
    }

    fun getAllCarts () {
        val id = context?.getPrefInt("user_id")

        Repository.getAllCarts(id!!.toInt()).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    Log.d("Success", it.data.toString())
                    view!!.rv_barang_checkout.setupNoAdapter(
                        R.layout.item_barang_checkout,
                        it.data!!,
                        LinearLayoutManager(context),
                        ::bindBarangRecyclerView
                    )

                    getCartPrice(it)

                }
                Resource.ERROR ->{
                    Log.d("Error", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })
    }

    private fun getCartPrice(it: Resource<List<Cart>>) {
        for (product in it.data!!) {
            subTotalPrice += (product.count * product.product.harga)
        }

        totalPrice += subTotalPrice

        view!!.tv_subtotal_produk.text = subTotalPrice.toString().toRupiahs()
        view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
    }

    fun bindBarangRecyclerView (view: View,cart: Cart) {
        Picasso.get().load(cart.product.gambar.url).into(view.iv_product_banner_cart)
        view.tv_store_name.text = cart.umkm.nama
        view.tv_title_product_cart.text = cart.product.title
        view.tv_price_product_cart.text = cart.product.harga.toString().toRupiahs()
        view.tv_count_product.text = "x"+ cart.count.toString()
    }

    fun getJenisPengirimanData () : ArrayList<JenisPengiriman>  {

        var jenisPengiriman : ArrayList<JenisPengiriman> = ArrayList()

        Repository.getAllJenisPengiriman().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    jenisPengiriman.addAll(it.data!!)
                    Log.d("Success", it.data.toString())
                }
                Resource.ERROR ->{
                    Log.d("Error", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })

        return jenisPengiriman
    }

    fun calculateDonation () : Int {
        var subTotalPrice = subTotalPrice + deliveryPrice

        return if (subTotalPrice % 1000 == 0)
            1000
        else {
            1000 - (subTotalPrice % 1000)
        }
    }

}
