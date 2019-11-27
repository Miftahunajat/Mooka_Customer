package com.example.mooka_customer.network

import com.example.mooka_customer.network.model.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getAllUsers(): Deferred<Response<ListResponse<User>>>

    @GET("users/{user_id}")
    fun getUserDetail(
        @Path("user_id") id: Int
    ): Deferred<Response<User>>

    @GET("products/{product_id}")
    fun getProductDetail(
        @Path("product_id") id: Int
    ): Deferred<Response<Product>>

    @GET("umkms/{umkm_id}")
    fun getUmkmDetail(
        @Path("umkm_id") id: Int
    ): Deferred<Response<UMKM>>

    @GET("umkms")
    fun getAllUmkms() : Deferred<Response<ListResponse<UMKM>>>

    @GET("products")
    fun getAllProducts() : Deferred<Response<ListResponse<Product>>>

    @GET("users/{user_id}/notifications")
    fun  getAllNotifications(
        @Path("user_id") id: Int
    ) : Deferred<Response<ListResponse<Notification>>>

    @FormUrlEncoded
    @POST("users/{user_id}/carts")
    fun  addToCart(
        @Path("user_id") userId: Int,
        @Field("umkm_id") umkmId: Int,
        @Field("product_id") productId: Int
    ) : Deferred<Response<Cart>>

    @GET("users/{user_id}/carts")
    fun  getAllCarts(
        @Path("user_id") id: Int
    ) : Deferred<Response<ListResponse<Cart>>>

    @FormUrlEncoded
    @POST("users/{user_id}/carts/checkout")
    fun  checkout(
        @Path("user_id") userId: String,
        @Field("pengiriman_id") pengirimanId: Int?,
        @Field("donasi") donasi: Int
    ) : Deferred<Response<Cart>>

    @GET("pengirimen")
    fun getAllJenisPengiriman () : Deferred<Response<ListResponse<JenisPengiriman>>>
}