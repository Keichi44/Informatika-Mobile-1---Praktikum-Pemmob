package com.unsoed.informatikamobile.data.network

import com.unsoed.informatikamobile.utils.Constants
import okhttp3.OkHttpClient // <-- IMPORT BARU
import okhttp3.logging.HttpLoggingInterceptor // <-- IMPORT BARU
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Ganti 'api' menjadi 'apiService' atau nama lain yang lebih deskriptif jika Anda mau, tapi 'api' juga tidak masalah.
    val api: OpenLibraryApi by lazy {

        // 1. Buat Logging Interceptor untuk melihat request & response di Logcat
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Level BODY akan log semua detail
        }

        // 2. Buat OkHttpClient dan tambahkan interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // 3. Bangun Retrofit dengan client yang sudah dimodifikasi
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client) // <-- GUNAKAN HTTP CLIENT YANG BARU
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApi::class.java)
    }
}