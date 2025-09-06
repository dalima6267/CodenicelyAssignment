package com.dalima.wikipedia_codenicely_assignment.viewmodel

import androidx.lifecycle.*
import com.dalima.wikipedia_codenicely_assignment.db.ImageEntity
import com.dalima.wikipedia_codenicely_assignment.network.WikiRepository
import kotlinx.coroutines.launch

class FeaturedViewModel(private val repo: WikiRepository) : ViewModel() {
    private val _images = MutableLiveData<List<ImageEntity>>()
    val images: LiveData<List<ImageEntity>> = _images

    private var continueToken: String? = null
    private var loading = false

    fun loadInitial() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchFeaturedImages(null)
            _images.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading || continueToken == null) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchFeaturedImages(continueToken)
            val cur = _images.value?.toMutableList() ?: mutableListOf()
            cur.addAll(list)
            _images.postValue(cur)
            continueToken = token
            loading = false
        }
    }
}

class FeaturedViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = FeaturedViewModel(repo) as T
}
