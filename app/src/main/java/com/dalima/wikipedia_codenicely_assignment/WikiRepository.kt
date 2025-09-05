package com.dalima.wikipedia_codenicely_assignment

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WikiRepository(private val dao: WikiDao) {

    private val wikiService = RetrofitClient.wikiService
    private val commonsService = RetrofitClient.commonsService

    // RANDOM
    suspend fun fetchRandomArticles(continueToken: String? = null): Pair<List<ArticleEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                // call
                val response = wikiService.randomArticles(grncontinue = continueToken)

                // map pages -> ArticleEntity
                val pages = response.query?.pages ?: emptyList()
                val existingIds = dao.getAllArticleIds().toHashSet()
                val newEntities = pages.mapNotNull { p ->
                    val id = p.pageid ?: return@mapNotNull null
                    if (existingIds.contains(id)) return@mapNotNull null
                    ArticleEntity(
                        pageId = id,
                        title = p.title ?: "(untitled)",
                        snippet = p.terms?.description?.firstOrNull() ?: p.extract,
                        content = p.extract,
                        imageUrl = p.thumbnail?.source
                    )
                }

                if (newEntities.isNotEmpty()) dao.insertArticles(newEntities)

                val next = response.cont?.grncontinue
                if (next != null) dao.insertContinueToken(ContinueTokensEntity("random", next))

                Pair(newEntities, next)
            } catch (e: Exception) {
                Log.e("WikiRepo", "fetchRandomArticles failed: ${e.message}", e)
                Pair(dao.getAllArticles(), null)
            }
        }
    }

    // FEATURED IMAGES (Commons)
    suspend fun fetchFeaturedImages(continueToken: String? = null): Pair<List<ImageEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = commonsService.featuredImages(gcmcontinue = continueToken)
                val pages = response.query?.pages ?: emptyList()
                val images = pages.mapNotNull { p ->
                    val info = p.imageinfo?.firstOrNull() ?: return@mapNotNull null
                    ImageEntity(
                        url = info.url ?: return@mapNotNull null,
                        user = info.user,
                        timestamp = info.timestamp,
                        title = p.title
                    )
                }
                if (images.isNotEmpty()) dao.insertImages(images)
                val next = response.cont?.gcmcontinue
                if (next != null) dao.insertContinueToken(ContinueTokensEntity("featured", next))
                Pair(images, next)
            } catch (e: Exception) {
                Log.e("WikiRepo", "fetchFeaturedImages failed: ${e.message}", e)
                Pair(dao.getAllImages(), null)
            }
        }
    }

    // CATEGORIES
    suspend fun fetchCategories(prefix: String = "List of", continueToken: String? = null): Pair<List<CategoryEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = wikiService.categories(acprefix = prefix, accontinue = continueToken)
                val cats = response.query?.allcategories?.mapNotNull { it.category?.let { CategoryEntity(it) } } ?: emptyList()
                if (cats.isNotEmpty()) dao.insertCategories(cats)
                val next = response.cont?.accontinue
                if (next != null) dao.insertContinueToken(ContinueTokensEntity("categories", next))
                Pair(cats, next)
            } catch (e: Exception) {
                Log.e("WikiRepo", "fetchCategories failed: ${e.message}", e)
                Pair(dao.getAllCategories(), null)
            }
        }
    }

    // local helpers
    suspend fun localArticles() = dao.getAllArticles()
    suspend fun localImages() = dao.getAllImages()
    suspend fun localCategories() = dao.getAllCategories()
    suspend fun getContinueToken(endpoint: String) = dao.getContinueToken(endpoint)
}
