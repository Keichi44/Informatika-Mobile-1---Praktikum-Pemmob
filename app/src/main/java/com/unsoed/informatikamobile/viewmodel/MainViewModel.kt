package com.unsoed.informatikamobile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsoed.informatikamobile.data.model.BookDoc
import com.unsoed.informatikamobile.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _books = MutableLiveData<List<BookDoc>>()
    val books: LiveData<List<BookDoc>> = _books

    // TAMBAHKAN DUA BARIS INI
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchBooks(query: String, page: Int = 1) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                // Teruskan parameter 'page' ke pemanggilan API
                val response = RetrofitInstance.api.searchBooks(query = query, page = page)

                // --- PERUBAHAN DI SINI ---
                // Periksa apakah response berhasil dan memiliki body
                if (response.isSuccessful && response.body() != null) {
                    // Ambil daftar buku dari body response
                    _books.postValue(response.body()?.docs)
                } else {
                    // Jika gagal, post list kosong atau tangani error
                    Log.e("MainViewModel", "Gagal fetchBooks: Response tidak berhasil atau body kosong")
                    _books.postValue(emptyList())
                }

            } catch (e: Exception) {
                Log.e("MainViewModel", "Gagal fetchBooks: ${e.message}", e)
                _books.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}