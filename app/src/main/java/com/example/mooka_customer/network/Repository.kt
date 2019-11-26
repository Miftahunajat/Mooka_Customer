package com.example.mooka_customer.network

import com.example.mooka_customer.Config
import com.example.mooka_customer.network.lib.networkCall
import com.example.mooka_customer.network.model.ListResponse
import com.example.mooka_customer.network.model.Product
import com.example.mooka_customer.network.model.UMKM
import com.example.mooka_customer.network.model.User
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

}
//    fun saveUser(user: UserResponse, context: Context){
//        val gson = Gson()
//        val json = gson.toJson(user)
//        context.getSharedPreferences(PREFNAME, 0).edit().putString("user",json).apply()
//    }
//
//    fun getUser(context: Context): UserResponse? {
//        val jsonString : String? = context.getSharedPreferences(PREFNAME, 0).getString("user",null)
//        val gson = Gson()
//        return if (jsonString != null) gson.fromJson(jsonString, UserResponse::class.java) else null
//    }
//
//    fun clearUser(context: Context) {
//        context.getSharedPreferences(PREFNAME, 0).edit().clear().apply()
//    }
//
//    fun getRepos(query: String) = networkCall<ListResponse<Repo>, List<Repo>> {
//        client = ManagemenApi.apiService.getRepos(query)
//    }
//
//    fun login(nama: String, password: String) = networkCall<UserResponse, UserResponse> {
//        client = ManagemenApi.apiService.login(nama, password)
//    }
//
//    fun getAllIuranBulanIni(id:Int, bulan: String, tahun: String) = networkCall<UpdateIuranResponse, UpdateIuranResponse> {
//        client = ManagemenApi.apiService.getIuranBulanIni(id,bulan, tahun)
//    }
//
//    fun getAllIuranTahunIni(id:Int, tahun: String) = networkCall<IuranPerTahunResponse, IuranPerTahunResponse> {
//        client = ManagemenApi.apiService.getIuranTahunIni(id, tahun)
//    }
//
//    fun getAllUser() = networkCall<ListResponse<UserResponse>, List<UserResponse>> {
//        client = ManagemenApi.apiService.getAllUser()
//    }
//
//    fun getAllKKUser() = networkCall<ListResponse<UserResponse>, List<UserResponse>> {
//        client = ManagemenApi.apiService.getAllKkUser()
//    }
//
//    fun updateIuranResponse(
//        id: Int, bulan: String, tahun: String, type: String, bayar: Boolean
//    ) = networkCall<IuranBulanResponse, IuranBulanResponse> {
//        client = ManagemenApi.apiService.updateIuran(id, bulan, tahun, type, bayar)
//    }
//
//    fun postArisan(
//        jenisKelaminId: Int, selesai: String, iuran: String, nama: String
//    ) = networkCall<Arisan, Arisan> {
//        client = ManagemenApi.apiService.postArisan(jenisKelaminId, selesai, iuran, nama)
//    }
//
//    fun getAllArisan(jenis_kelamin_id: Int) = networkCall<ListResponse<Arisan>, List<Arisan>> {
//        client = ManagemenApi.apiService.getAllArisans(jenis_kelamin_id.toString())
//    }
//
//    fun getAllArisansWithStatusIKutUser(user_id: String, jenis_kelamin_id: Int) = networkCall<ListResponse<Arisan>, List<Arisan>> {
//        client = ManagemenApi.apiService.getAllArisansWithStatusIKutUser(user_id,jenis_kelamin_id.toString())
//    }
//
//    fun getArisansUser(arisans_user: Int) = networkCall<AllUserArisanResponse, AllUserArisanResponse> {
//        client = ManagemenApi.apiService.getArisansUser(arisans_user)
//    }
//
//    fun getAllStatusArisan(arisan_id: Int) = networkCall<ListResponse<AllUserArisanResponse>, List<AllUserArisanResponse>> {
//        client = ManagemenApi.apiService.getAllStatusArisanUser(arisan_id)
//    }
//
//    fun updateArisan(arisans_user_id: Int, bulan: String, tahun: String, bayar: Boolean) = networkCall<UserBayarArisan, UserBayarArisan> {
//        client = ManagemenApi.apiService.updateArisan(arisans_user_id.toString(), bulan, tahun, bayar)
//    }
//
//    fun getDetailUserStatus(id: Int, tahun: String) = networkCall<ListResponse<UserBayarArisan>,List<UserBayarArisan>> {
//        client = ManagemenApi.apiService.getDetailUserStatus(id, tahun)
//    }
//
//    fun postDaftarArisan(id: Int, user_id: String) = networkCall<DaftarArisanResponse,DaftarArisanResponse> {
//        client = ManagemenApi.apiService.postDaftarArisan(id, user_id)
//    }
//
//    fun postIkutArisan(id: Int) = networkCall<IkutArisanResponse,IkutArisanResponse> {
//        client = ManagemenApi.apiService.postIkutArisan(id)
//    }
//
//    fun postTarikArisan(id: Int) = networkCall<IkutArisanResponse,IkutArisanResponse> {
//        client = ManagemenApi.apiService.postTarikArisan(id)
//    }
//
//    fun updateUser(
//        id: Int,
//        nama: String? = null,
//        password: String? = null,
//        alamat: String? = null,
//        no_hp: String? = null
//    ) = networkCall<UserResponse,UserResponse> {
//        client = ManagemenApi.apiService.updateUser(
//            id,
//            nama,
//            password,
//            alamat,
//            no_hp
//        )
//    }
//
//    fun getUserDetail(id: Int) = networkCall<UserResponse,UserResponse> {
//        client = ManagemenApi.apiService.getUserDetail(id)
//    }
//
//    fun getHargaIuranByCode(code: Int) = networkCall<HargaIuranResponse,HargaIuranResponse> {
//        client = ManagemenApi.apiService.getHargaIuranByCode(code)
//    }
//
//    fun updateHargaIuran(id: Int, harga: String) = networkCall<HargaIuranResponse,HargaIuranResponse> {
//        client = ManagemenApi.apiService.updateHargaIuran(id, harga)
//    }
//
//    fun getAllPengunguman() = networkCall<ListResponse<PengungumanResponse>,List<PengungumanResponse>> {
//        client = ManagemenApi.apiService.getAllPengunguman()
//    }
//
//    fun getPengunguman(id: Int) = networkCall<PengungumanResponse,PengungumanResponse> {
//        client = ManagemenApi.apiService.getPengunguman(id)
//    }
//
//    fun postPengunguman(title: String, body: String, content: String, contentDesc: String) = networkCall<PengungumanResponse,PengungumanResponse> {
//        client = ManagemenApi.apiService.postPengunguman(title, body, content, contentDesc)
//    }
//
//    fun getPengeluaranPertahun(tahun: String) = networkCall<ListResponse<DataKasRTResponse>,List<DataKasRTResponse>> {
//        client = ManagemenApi.apiService.pengeluaranPerTahun(tahun)
//    }
//
//    fun postPengeluaran(tahun: String, keterangan: String, nama_bulan: String, jumlah: String) = networkCall<ListResponse<DataKasRTResponse>,List<DataKasRTResponse>> {
//        client = ManagemenApi.apiService.postListPengeluaran(tahun, keterangan, nama_bulan, jumlah)
//    }
//
//    fun deletePengeluaran(id: Int) = networkCall<Pengeluaran,Pengeluaran> {
//        client = ManagemenApi.apiService.deleteListPengeluaran(id.toString())
//    }

//    fun getKeluargas(id: Int) = networkCall<ListResponse<UserResponse>,List<UserResponse>> {
//        client = ManagemenApi.apiService.getKeluargas(id.toString())
//    }
//
//    fun updateArisan(id: Int, tutup: Boolean) = networkCall<Arisan,Arisan> {
//        client = ManagemenApi.apiService.updateArisan(id.toString(),tutup.toString())
//    }
//
//    fun postPengungumanPhoto(title: MultipartBody.Part, body: MultipartBody.Part, content: MultipartBody.Part?, contentDesc: MultipartBody.Part) = networkCall<PengungumanResponse,PengungumanResponse> {
//        client = ManagemenApi.apiService.postPengungumanPhoto(title, body, content, contentDesc)
//    }
//
//    fun getTotalPengeluaran() = networkCall<TotalPengeluaranResponse,TotalPengeluaranResponse> {
//        client = ManagemenApi.apiService.getTotalPengeluaran()
//    }
//}

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

    var apiService: ApiService = retrofit.create<ApiService>(ApiService::class.java)
    val PREFNAME = "mooka"
}
