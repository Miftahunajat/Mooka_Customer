package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse

data class Notification(
    val created_at: String,
    val id: Int,
    val judul: String,
    val text: String,
    val updated_at: String,
    val tipe: String,
    val user_id: Int
) : DataResponse<Notification> {
    override fun retrieveData(): Notification = this
}