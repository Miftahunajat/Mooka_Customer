package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

/**
 *
 * Created by aflah on 04/02/20
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


data class ResponseCekPembayaran (
    val status:Boolean,
    val responseDescription: String,
    val responseCode: Int,
    val data: DataCekPembayaran
) : DataResponse<ResponseCekPembayaran> {
    override fun retrieveData(): ResponseCekPembayaran = this
}