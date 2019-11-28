package com.example.mooka_customer.network

import com.example.mooka_customer.Config
import com.example.mooka_customer.network.lib.networkCall
import com.example.mooka_customer.network.model.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.util.concurrent.TimeUnit


object Repository {
    fun getAllUsers() = networkCall<ListResponse<User>, List<User>> {
        client = ManagemenApi.apiService.getAllUsers()
    }

    fun getAllUmkms() = networkCall<ListResponse<UMKM>, List<UMKM>> {
        client = ManagemenApi.apiService.getAllUmkms()
    }

    fun getAllProducts() = networkCall<ListResponse<Product>, List<Product>> {
        client = ManagemenApi.apiService.getAllProducts()
    }

    fun getProductDetail(id: Int) = networkCall<Product, Product> {
        client = ManagemenApi.apiService.getProductDetail(id)
    }

    fun getUmkmDetail(id: Int) = networkCall<UMKM, UMKM> {
        client = ManagemenApi.apiService.getUmkmDetail(id)
    }

    fun getAllCarts(userId: Int) = networkCall<ListResponse<Cart>, List<Cart>> {
        client = ManagemenApi.apiService.getAllCarts(userId)
    }

    fun postToCart(userId: Int, umkmId: Int, productId: Int) = networkCall<Cart, Cart> {
        client = ManagemenApi.apiService.addToCart(userId, umkmId, productId)
    }

    fun checkout(userId: String, pengrimanId: Int?, donasi:Int) = networkCall<Cart, Cart> {
        client = ManagemenApi.apiService.checkout(userId, pengrimanId, donasi)
    }

    fun getAllJenisPengiriman() = networkCall<ListResponse<JenisPengiriman>, List<JenisPengiriman>> {
        client = ManagemenApi.apiService.getAllJenisPengiriman()
    }

    fun getAllNotifications(userId: Int) = networkCall<ListResponse<Notification>, List<Notification>> {
        client = ManagemenApi.apiService.getAllNotifications(userId)
    }
}


object ManagemenApi {
    var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var API_BASE_URL: String = Config.API_BASE_URL
    var httpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
    var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
    var retrofit = builder
        .client(httpClient.build())
        .build()

    var apiService: ApiService = retrofit.create(ApiService::class.java)
    val PREFNAME = "mooka"
}
