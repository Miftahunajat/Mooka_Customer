package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

/**
 *
 * Created by aflah on 04/02/20
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


class ResponseBuatPembayaran (
    val status: Boolean,
    val errDesc: String,
    val responseCode: Int,
    val data: DataBuatPembayaran
) : DataResponse<ResponseBuatPembayaran> {
    override fun retrieveData(): ResponseBuatPembayaran = this
}