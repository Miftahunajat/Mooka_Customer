package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

/**
 *
 * Created by aflah on 27/11/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


data class JenisPengiriman (
    val id : Int,
    val nama : String,
    val harga : Int
) : DataResponse<JenisPengiriman> {
    override fun retrieveData(): JenisPengiriman = this
}