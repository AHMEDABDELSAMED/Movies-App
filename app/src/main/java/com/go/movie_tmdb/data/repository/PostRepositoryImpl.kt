package com.go.movie_tmdb.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.go.movie_tmdb.domain.model.Post
import com.go.movie_tmdb.data.local.PostDao
import com.go.movie_tmdb.data.remote.PostApiService
import com.go.movie_tmdb.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val apiService: PostApiService,
    private val postDao: PostDao
) : PostRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(apiService, postDao),
            pagingSourceFactory = { postDao.getAllPosts() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    override fun searchPagedPosts(query: String): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { SearchPostsPagingSource(apiService, query) }
        ).flow
    }

}