package com.dalima.wikipedia_codenicely_assignment
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
            val (list, token) = repo.fetchFeaturedImages(
                url = "w/api.php?action=query&generator=categorymembers&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&prop=imageinfo&iiprop=url|user|timestamp&format=json"
            )
            _images.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading || continueToken == null) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchFeaturedImages(
                url = "w/api.php?action=query&generator=categorymembers&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&prop=imageinfo&iiprop=url|user|timestamp&format=json&gcmcontinue=$continueToken"
            )
            val current = _images.value?.toMutableList() ?: mutableListOf()
            current.addAll(list)
            _images.postValue(current)
            continueToken = token
            loading = false
        }
    }
}


class FeaturedViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeaturedViewModel(repo) as T
    }
}