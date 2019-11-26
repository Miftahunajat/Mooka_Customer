package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

data class Product(
    val buy_count: Int,
    val created_at: String,
    val gambar: Gambar,
    val harga: Int,
    val id: Int,
    val rating: String,
    val stock: Int,
    val title: String,
    val umkm_id: Int,
    val updated_at: String,
    val umkm: UMKM
) : DataResponse<Product> {
    override fun retrieveData(): Product = this
}