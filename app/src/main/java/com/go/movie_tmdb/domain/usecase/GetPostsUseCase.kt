package com.go.movie_tmdb.domain.usecase



import androidx.paging.PagingData
import com.go.movie_tmdb.domain.model.Post
import com.go.movie_tmdb.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetPostsUseCase(
    private val repository: PostRepository
) {
    fun getPostsFlow(): Flow<PagingData<Post>> = flow {
        emitAll(repository.getPagedPosts())
    }.catch { e ->
        throw e
    }
    fun searchPostsFlow(query: String): Flow<PagingData<Post>> = flow {
        emitAll(repository.searchPagedPosts(query))
    }.catch { e ->
        throw e
    }


}



