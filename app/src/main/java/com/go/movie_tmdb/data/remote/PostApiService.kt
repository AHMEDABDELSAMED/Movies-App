package com.go.movie_tmdb.data.remote

import com.go.movie_tmdb.domain.model.MoviesResponse
import com.go.movie_tmdb.domain.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import kotlinx.coroutines.delay

class PostApiService(private val client: HttpClient) {
    // fetch Movies
    suspend fun fetchPosts(page: Int): List<Post> {
        val maxRetries = 3

        repeat(maxRetries) { attempt ->
            try {
                val response: MoviesResponse = client.get("https://api.themoviedb.org/3/discover/movie") {
                    parameter("api_key", "25a106ac989fe8bfda1dfa5c92602aa4")
                    parameter("page", page)
                }.body()

                println("Page: $page, get posts: ${response.results.size}")
                return response.results
            } catch (e: Exception) {
                if (attempt == maxRetries - 1) {
                    e.printStackTrace()
                    return emptyList()
                }
                kotlinx.coroutines.delay(1000)
            }
        }

        return emptyList() // fallback
    }
    suspend fun searchMovies(query: String, page: Int): List<Post> {
        val maxRetries = 3

        repeat(maxRetries) { attempt ->
            try {
                val response: MoviesResponse = client.get("https://api.themoviedb.org/3/search/movie") {
                    parameter("api_key", "25a106ac989fe8bfda1dfa5c92602aa4")
                    parameter("query", query)
                    parameter("page", page)
                }.body()

                println("Search for \"$query\" got: ${response.results.size} results (page $page)")
                return response.results
            } catch (e: Exception) {
                if (attempt == maxRetries - 1) {
                    e.printStackTrace()
                    return emptyList()
                }
                delay(1000)
            }
        }

        return emptyList()
    }


}
