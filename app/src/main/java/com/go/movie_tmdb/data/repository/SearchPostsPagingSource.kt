package com.go.movie_tmdb.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.go.movie_tmdb.domain.model.Post
import com.go.movie_tmdb.data.remote.PostApiService

class SearchPostsPagingSource(
    private val apiService: PostApiService,
    private val query: String
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 1
            val posts = apiService.searchMovies(query, page) // 👈 page هنا

            LoadResult.Page(
                data = posts,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? = null
}