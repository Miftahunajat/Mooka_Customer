package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

data class Cart(
    val count: Int,
    val created_at: String,
    val id: Int,
    val product_id: Int,
    val umkm_id: Int,
    val updated_at: String,
    val user_id: Int,
    val product: Product,
    val umkm: UMKM
) : DataResponse<Cart> {
    override fun retrieveData(): Cart = this
}