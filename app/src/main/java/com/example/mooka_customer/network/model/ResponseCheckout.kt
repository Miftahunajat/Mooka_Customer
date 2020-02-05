package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse
import java.util.*

/**
 *
 * Created by aflah on 05/02/20
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


data class ResponseCheckout (
    val id:Int,
    val jumlah: Int,
    val user_id: Int,
    val status: String,
    val created_at: Date,
    val updated_at: Date
) :DataResponse<ResponseCheckout> {
    override fun retrieveData(): ResponseCheckout = this
}