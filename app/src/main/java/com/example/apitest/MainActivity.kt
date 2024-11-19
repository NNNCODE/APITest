package com.example.apitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apitest.api.Post
import com.example.apitest.ui.theme.APITestTheme
import com.example.apitest.viewmodel.MainViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: MainViewModel by viewModels()
        setContent {
            APITestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // 使用 LaunchedEffect 在 Compose 中发起网络请求
                        LaunchedEffect(key1 = true) {
                            viewModel.fetchPosts()
                        }

                        // 显示数据
                        when {
                            viewModel.isLoading -> {
                                LoadingScreen()
                            }

                            viewModel.error != null -> {
                                ErrorScreen(error = viewModel.error)
                            }

                            else -> {
                                PostList(posts = viewModel.posts)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorScreen(error: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Error: $error",
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = post.body)
    }
}


