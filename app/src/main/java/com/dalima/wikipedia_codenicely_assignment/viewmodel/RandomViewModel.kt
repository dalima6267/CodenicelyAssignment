package com.dalima.wikipedia_codenicely_assignment.viewmodel

import androidx.lifecycle.*
import com.dalima.wikipedia_codenicely_assignment.db.ArticleEntity
import com.dalima.wikipedia_codenicely_assignment.network.WikiRepository
import kotlinx.coroutines.launch

class RandomViewModel(private val repo: WikiRepository) : ViewModel() {
    private val _articles = MutableLiveData<List<ArticleEntity>>()
    val articles: LiveData<List<ArticleEntity>> = _articles

    private var continueToken: String? = null
    private var loading = false

    fun loadInitial() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchRandomArticles(null)
            _articles.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading || continueToken == null) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchRandomArticles(continueToken)
            val cur = _articles.value?.toMutableList() ?: mutableListOf()
            cur.addAll(list)
            _articles.postValue(cur)
            continueToken = token
            loading = false
        }
    }
}

class RandomViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = RandomViewModel(repo) as T
}
