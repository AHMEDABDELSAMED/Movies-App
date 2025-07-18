package com.go.movie_tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.go.movie_tmdb.domain.model.PostEntity
import com.go.movie_tmdb.domain.model.toEntity
import com.go.movie_tmdb.data.local.PostDao
import com.go.movie_tmdb.data.remote.PostApiService

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val apiService: PostApiService,
    private val postDao: PostDao,
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>,
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else lastItem.pageNumber + 1
                }
            }
            val posts = apiService.fetchPosts(page)
            if (posts.isNotEmpty()) {
                postDao.insertPosts(posts.map { it.toEntity(page) })
            }
            MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (e: Exception) {
            println("Error: $e")
            MediatorResult.Error(e)
        }
    }


}
