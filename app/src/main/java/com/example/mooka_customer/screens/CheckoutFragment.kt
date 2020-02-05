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
import com.example.mooka_customer.service.NotificationService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import kotlinx.android.synthetic.main.item_barang_checkout.view.*

/**
 * A simple [Fragment] subclass.
 */
class CheckoutFragment : Fragment() {

    private var subTotalPrice = 0
    private var deliveryPrice = 0
    private var totalPrice = 0
    private var additionalDonation = 0

    private var selectedIdJenisPengiriman:Int = 0

    private var jenisPengirimanList :List<JenisPengiriman> = ArrayList()

    private var listCart = mutableListOf<Cart>()

    private var donation: Int = 0

    private var userName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        jenisPengirimanList = getJenisPengirimanData()

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
                donation += calculateDonation()

                view.tv_pembulatan_donasi.text = "Pembulatan Donasi sebesar " +  calculateDonation()
                view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()

                view.constraintLayoutDonasiTambahan.visibility = View.VISIBLE
            } else {
                totalPrice-= calculateDonation()
                donation -= calculateDonation()

                view.tv_pembulatan_donasi.text = " "
                view!!.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()

                view.constraintLayoutDonasiTambahan.visibility = View.GONE
            }
        }

        view.checkBox_tambah_donasi.addEventDialogListener {
            if (!it.isChecked) {
                totalPrice -= additionalDonation
                additionalDonation = 0

                view!!.tv_donasi_tambahan.text = " "
                view.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
            } else {
                context!!.showEditableBottomSheetDialog("Donasi tamabahn", "Jumlah donasi tambahan", InputType.TYPE_CLASS_NUMBER ){
                    additionalDonation += it.toInt()

                    totalPrice += additionalDonation


                    view!!.tv_donasi_tambahan.text = "Donasi tambahan " + it
                    view.tv_total_pembayaran.text = totalPrice.toString().toRupiahs()
                }
            }
        }

        view.btn_bayar_checkout.setOnClickListener {

            val noTelp = context!!.getPrefString("user_no_telp")
            val noTelpSubString = noTelp!!.substring(noTelp.length-8-1)

            if (view.tv_address_pengiriman.text.toString().isEmpty() || view.tv_biaya_pengiriman.text.toString().isEmpty()) {
                context?.showAlertDialog("Warning", "Silahkan isi alamat dan pilih jasa pengiriman!")
            } else {
                doCheckout(noTelpSubString)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val idUser = context!!.getPrefInt("user_id")
        getUserName(idUser)
        getAllCarts()
    }

    private fun doCheckout(noTelp: String) {
        val id = context?.getPrefInt("user_id")

        Repository.checkout(
            id!!.toString(),
            selectedIdJenisPengiriman,
            donation + additionalDonation
        ).observe(this, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS -> {

                    Log.e("idcheckout", it.data!!.id.toString())
                    createPembayaranBriva(it.data!!.id, noTelp)

                    context!!.showAlertDialog(
                        "Checkout Berhasil",
                        "Selamat, barang yang Anda yang ada dikeranjang berhasil dibeli..."
                    ) {
                        findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToDetailTagihanFragment(it.data!!.id.toString()+ noTelp))
//                        findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment())
                    }

                    NotificationService.getInstance(context)
                        .backcheckingSelesaiNotification(
                            "Transfer Donasi berhasil",
                            "Terimakasih telah melakukan donasi sebesar ${donation + additionalDonation} kepada MIKA," +
                                    "Dengan donasi anda maka anda turut dalam membangun IKM - IKM yang ada di indonesia",
                            "Donasi sebesar ${donation + additionalDonation} telah berhasil"
                        )

                    listCart.forEach {
                        NotificationService
                            .getInstance(context)
                            .sendNotifToUmkm(
                                it.umkm_id.toString(),
                                "Barang Terjual",
                                "Barang anda ${it.product.title} berhasil terrjual"
                            )
                    }

                    Log.d("Success checkout", it.data.toString())
                }
                Resource.ERROR -> {

                    if (it.status == 92) {
                        context?.showmessage("Maaf saldo Anda tidak cukup")
                    } else {
                        context?.showmessage("Something is wrong" + it.status)
                    }
                    Log.d("Error", it.message!!)
                }
            }
        })
    }

    private fun createPembayaranBriva(
        idCheckout: Int,
        noTelp: String
    ) {
        val custCode = idCheckout.toString() + noTelp
        Repository.buatPembayaran(custCode, userName, totalPrice).observe(this, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS -> {
                    Log.e("Success briva", it.data.toString())
                }
                Resource.ERROR -> {
                    Log.d("Error", it.message!!)
                    context?.showmessage("Something is wrong")
                }
            }
        })

        if (context!!.getPrefSetString("custcode").isNullOrEmpty()) {
            Log.e("lele", "null atau empty")
            context!!.savePref("custcode", mutableSetOf(custCode))
        } else {
            Log.e("lele", "tidak null")
            var custcodes = context!!.getPrefSetString("custcode")!!.toMutableSet()
            custcodes.add(custCode)
            context!!.savePref("custcode", custcodes)
            Log.e("custcodenya", custcodes.toString())
        }
    }

    private fun getAllCarts () {
        val id = context?.getPrefInt("user_id")

        Repository.getAllCarts(id!!.toInt()).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    listCart = it.data!!.toMutableList()
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

    private fun bindBarangRecyclerView (view: View, cart: Cart) {
        Picasso.get().load(cart.product.gambar.url).into(view.iv_product_banner_cart)
        view.tv_store_name.text = cart.umkm.nama
        view.tv_title_product_cart.text = cart.product.title
        view.tv_price_product_cart.text = cart.product.harga.toString().toRupiahs()
        view.tv_count_product.text = "x"+ cart.count.toString()
    }

    private fun getJenisPengirimanData () : ArrayList<JenisPengiriman>  {

        val jenisPengiriman : ArrayList<JenisPengiriman> = ArrayList()

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

    private fun calculateDonation () : Int {
        val subTotalPrice = subTotalPrice + deliveryPrice

        return if (subTotalPrice % 1000 == 0)
            1000
        else {
            1000 - (subTotalPrice % 1000)
        }
    }

    private fun getUserName(idUser:Int) {

        Repository.getUserDetail(idUser).observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    Log.d("Loading", it.status.toString())
                }
                Resource.SUCCESS ->{
                    this.userName = it.data!!.nama
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
