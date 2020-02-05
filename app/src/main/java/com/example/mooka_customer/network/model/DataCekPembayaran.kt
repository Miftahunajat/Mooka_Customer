package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse
import java.util.*

/**
 *
 * Created by aflah on 04/02/20
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


data class DataCekPembayaran(
    val institutionCode: String,
    val BrivaNo: Int,
    val CustCode: String,
    val Nama: String,
    val Amount: Int,
    val Keterangan: String,
    val statusBayar: String,
    val expiredDate: String,
    val lastUpdate: String
)