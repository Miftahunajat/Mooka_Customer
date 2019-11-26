package com.example.mooka_customer.screens


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooka_customer.R
import com.example.mooka_customer.extension.setupNoAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

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
        view!!.rv_rekomendasi.setupNoAdapter(
            R.layout.item_product_rekomendasi,
            listOf("1","2","3","4","5"),
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ) {_,_->

        }

    }

    private fun setupPopuler(view: View?) {
        view!!.rv_populer.setupNoAdapter(
            R.layout.item_product_populer,
            listOf("1","2","3","4","5"),
            GridLayoutManager(context, 3)
        ) { _, _->

        }
    }


}
