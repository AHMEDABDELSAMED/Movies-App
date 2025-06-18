package com.go.movie_tmdb.presentation.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import androidx.paging.cachedIn
import com.go.movie_tmdb.domain.model.Post
import com.go.movie_tmdb.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.io.IOException
class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    // Save the FlowSave
    val lastFeed: Flow<PagingData<Post>> = getPostsUseCase.getPostsFlow().cachedIn(viewModelScope)

    val searchQuery = MutableStateFlow("")
    private val _lastSearch = MutableStateFlow<Flow<PagingData<Post>>?>(null)
    val lastSearch: StateFlow<Flow<PagingData<Post>>?> = _lastSearch
    fun search(query: String) {
        searchQuery.value = query
        val flow = getPostsUseCase.searchPostsFlow(query).cachedIn(viewModelScope)
        _lastSearch.value = flow
    }
    // clear Search
    fun clearSearch() {
        _lastSearch.value = null
        searchQuery.value = ""
    }

}