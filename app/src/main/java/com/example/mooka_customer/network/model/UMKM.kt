package com.example.mooka_customer.network.model

import com.example.mooka_customer.network.lib.DataResponse


data class UMKM(
    val alamat: String,
    val created_at: String,
    val email: String,
    val gambar: Gambar,
    val id: Int,
    val jenis_umkm_id: Int,
    val kota: String,
    val logo: String,
    val nama: String,
    val nama_pemilik: String,
    val siup: Siup,
    val updated_at: String
) : DataResponse<UMKM> {
    override fun retrieveData(): UMKM = this
}