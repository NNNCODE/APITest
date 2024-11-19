package com.example.apitest.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.api.Post
import com.example.apitest.api.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: List<Post> get() = _posts.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value

    fun fetchPosts() {
        viewModelScope.launch {
            Log.d("API_TEST", "====== Starting API Call ======")
            try {
                _isLoading.value = true
                _error.value = null
                _posts.value = RetrofitClient.apiService.getPosts()
                Log.d("API_TEST", "Success: Got ${_posts.value.size} posts")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}")
                _error.value = e.message
                //add logs
                Log.e("API_TEST", "Error fetching posts", e)
            } finally {
                _isLoading.value = false
                Log.d("API_TEST", "====== API Call Completed ======")
            }
        }
    }
}