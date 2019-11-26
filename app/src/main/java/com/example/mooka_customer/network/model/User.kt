package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

data class User(
    val created_at: String,
    val email: String,
    val id: Int,
    val kota: String,
    val nama: String,
    val no_telfon: String,
    val password: String,
    val saldo: String,
    val total_donasi: String,
    val updated_at: String
)  : DataResponse<User> {
    override fun retrieveData(): User = this
}