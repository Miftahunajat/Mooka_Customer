package com.example.mooka_customer.network.model


data class DataBuatPembayaran(
    val institutionCode: String,
    val brivaNo: Int,
    val custCode: String,
    val nama: String,
    val amount: Int,
    val keterangan: String,
    val expiredDate: String
)