package com.example.mooka_customer.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        val custcodes = context!!.getPrefSetString("custcode")
        view.rv_tagihans.setupNoAdapter(
            R.layout.item_tagihans,
            custcodes!!.toList()
        ) { view, string -> view.tv_nomor_briva.text = string
            view.setOnClickListener {
                findNavController().navigate(
                    DaftarTagihanFragmentDirections.actionDaftarTagihanFragmentToPreviewTagihanFragment(
                        string
                    )
                )
            }
        }


    }
}
