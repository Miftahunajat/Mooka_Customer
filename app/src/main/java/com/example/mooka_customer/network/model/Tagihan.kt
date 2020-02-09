package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

data class Tagihan(
    val created_at: String,
    val id: Int,
    val jumlah: String,
    val number: Int,
    val status: String,
    val updated_at: String,
    val user_id: Int
) : DataResponse<Tagihan>{
    override fun retrieveData(): Tagihan = this
}