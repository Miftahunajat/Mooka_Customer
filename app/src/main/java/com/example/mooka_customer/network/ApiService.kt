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

    @GET("umkms")
    fun getAllUmkms() : Deferred<Response<ListResponse<UMKM>>>

    @GET("products")
    fun getAllProducts() : Deferred<Response<ListResponse<Product>>>

    @GET("users/{user_id}/notifications")
    fun  getAllNotifications(
        @Path("user_id") id: Int
    ) : Deferred<Response<ListResponse<Notification>>>


//    @GET("search/repositories")
//    fun getRepos(@Query("q") query: String): Deferred<Response<ListResponse<Repo>>>
//
//    @FormUrlEncoded
//    @POST("login")
//    fun login(@Field("nama") nama: String, @Field("password") password: String): Deferred<Response<UserResponse>>
//
//    @GET("user/{kk_id}/all_iuran")
//    fun getIuranBulanIni(
//        @Path("kk_id") id: Int,
//        @Query("bulan") namaBulan: String,
//        @Query("tahun") tahun: String
//    ): Deferred<Response<UpdateIuranResponse>>
//
//    @GET("user")
//    fun getAllUser(
//    ): Deferred<Response<ListResponse<UserResponse>>>
//
//    @GET("all_kk_user_with_iuran")
//    fun getAllKkUser(
//    ): Deferred<Response<ListResponse<UserResponse>>>
//
//    @GET("user/{user_id}")
//    fun getUserDetail(
//        @Path("user_id") id: Int
//    ): Deferred<Response<UserResponse>>
//
//    @FormUrlEncoded
//    @PUT("user/{user_id}")
//    fun updateUser(
//        @Path("user_id") id: Int,
//        @Field("nama") nama: String?,
//        @Field("password")password: String?,
//        @Field("alamat")alamat: String?,
//        @Field("no_hp")no_hp: String?
//    ): Deferred<Response<UserResponse>>
//
//    @GET("user/{kk_id}/iuran_sosial_sampah")
//    fun getIuranTahunIni(
//        @Path("kk_id") id: Int,
//        @Query("tahun") tahun: String
//    ): Deferred<Response<IuranPerTahunResponse>>
//
//    @FormUrlEncoded
//    @POST("user/{kk_id}/update_iuran")
//    fun updateIuran(
//        @Path("kk_id") id: Int,
//        @Field("bulan") bulan: String,
//        @Field("tahun") tahun: String,
//        @Field("type") type: String,
//        @Field("bayar") bayar: Boolean
//    ): Deferred<Response<IuranBulanResponse>>
//
//    @FormUrlEncoded
//    @POST("arisan")
//    fun postArisan(
//        @Field("jenis_kelamin_id") id: Int,
//        @Field("selesai") selesai: String,
//        @Field("iuran") iuran: String,
//        @Field("nama") nama: String
//    ): Deferred<Response<Arisan>>
//
//    @GET("arisan")
//    fun getAllArisans(@Query("jenis_kelamin_id") jenis_kelamin: String): Deferred<Response<ListResponse<Arisan>>>
//
//    @GET("user/{user_id}/all_arisan_daftar_ikut?jenis_kelamin_id=1")
//    fun getAllArisansWithStatusIKutUser(
//        @Path("user_id") user_id: String,
//        @Query("jenis_kelamin_id") jenis_kelamin: String
//    ): Deferred<Response<ListResponse<Arisan>>>
//
//    @GET("arisan/{id}/all_user")
//    fun getAllStatusArisanUser(@Path("id") arisan_id: Int): Deferred<Response<ListResponse<AllUserArisanResponse>>>
//
//    @GET("arisans_user/{arisans_user}/detail_user_status")
//    fun getDetailUserStatus(
//        @Path("arisans_user") id: Int,
//        @Query("tahun") tahun: String
//    ): Deferred<Response<ListResponse<UserBayarArisan>>>
//
//    @FormUrlEncoded
//    @POST("arisans_user/{arisans_user_id}/update_arisan")
//    fun updateArisan(
//        @Path("arisans_user_id") arisans_user_id: String,
//        @Field("bulan") bulan: String,
//        @Field("tahun") tahun: String,
//        @Field("bayar") bayar: Boolean
//    ): Deferred<Response<UserBayarArisan>>
//
//    @GET("arisans_user/{arisans_user_id}")
//    fun getArisansUser(
//        @Path("arisans_user_id") id: Int
//    ): Deferred<Response<AllUserArisanResponse>>
//
//    @FormUrlEncoded
//    @POST("arisan/{arisan_id}/daftar_arisan")
//    fun postDaftarArisan(
//        @Path("arisan_id") id: Int,
//        @Field("user_id") user_id: String
//    ): Deferred<Response<DaftarArisanResponse>>
//
//    @POST("arisans_user/{arisans_user_id}/ikut_arisan")
//    fun postIkutArisan(
//        @Path("arisans_user_id") id: Int
//    ): Deferred<Response<IkutArisanResponse>>
//
//    @POST("arisans_user/{arisans_user_id}/tarik_arisan")
//    fun postTarikArisan(
//        @Path("arisans_user_id") id: Int
//    ): Deferred<Response<IkutArisanResponse>>
//
//    @GET("harga_iuran")
//    fun getHargaIuranByCode(
//        @Query("code") code: Int
//    ): Deferred<Response<HargaIuranResponse>>
//
//    @FormUrlEncoded
//    @PUT("harga_iuran/{harga_iuran_id}")
//    fun updateHargaIuran(
//        @Path("harga_iuran_id") hargaIuranId: Int,
//        @Field("harga") harga: String?
//    ): Deferred<Response<HargaIuranResponse>>
//
//    @GET("pengunguman")
//    fun getAllPengunguman(): Deferred<Response<ListResponse<PengungumanResponse>>>
//
//    @GET("pengunguman/{id}")
//    fun getPengunguman(
//        @Path("id") id: Int
//    ): Deferred<Response<PengungumanResponse>>
//
//    @FormUrlEncoded
//    @POST("pengunguman")
//    fun postPengunguman(
//        @Field("title")title: String,
//        @Field("body")body: String,
//        @Field("content")content: String,
//        @Field("content_desc")descContent: String
//    ): Deferred<Response<PengungumanResponse>>
//
//    @GET("pengeluaran_per_tahun")
//    fun pengeluaranPerTahun(
//        @Query("tahun")tahun: String
//    ): Deferred<Response<ListResponse<DataKasRTResponse>>>
//
//    @FormUrlEncoded
//    @POST("list_pengeluaran")
//    fun postListPengeluaran(
//        @Field("tahun")tahun: String,
//        @Field("keterangan")keterangan: String,
//        @Field("nama_bulan")nama_bulan: String,
//        @Field("jumlah")jumlah: String
//    ): Deferred<Response<ListResponse<DataKasRTResponse>>>
//
//    @DELETE("list_pengeluaran/{pengeluaranId}")
//    fun deleteListPengeluaran(
//        @Path("pengeluaranId")pengeluaranId: String
//    ): Deferred<Response<Pengeluaran>>
//
//    @GET("user/{user_id}/keluarga")
//    fun getKeluargas(
//        @Path("user_id")user_id: String
//    ): Deferred<Response<ListResponse<UserResponse>>>
//
//    @FormUrlEncoded
//    @PUT("arisan/{arisan_id}")
//    fun updateArisan(
//        @Path("arisan_id")arisan_id: String,
//        @Field("tutup")tutup: String
//    ): Deferred<Response<Arisan>>
//
//    @Multipart
//    @POST("pengunguman")
//    fun postPengungumanPhoto(
//        @Part title: MultipartBody.Part,
//        @Part body: MultipartBody.Part,
//        @Part content: MultipartBody.Part?,
//        @Part descContent: MultipartBody.Part
//    ): Deferred<Response<PengungumanResponse>>
//
//    @GET("total_pengeluaran")
//    fun getTotalPengeluaran(): Deferred<Response<TotalPengeluaranResponse>>
}