package com.dalima.wikipedia_codenicely_assignment

import android.R.attr.content
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WikiRepository(private val dao: WikiDao) {

    private val wikiService = RetrofitClient.wikiService
    private val commonsService = RetrofitClient.commonsService

    // -------------------- RANDOM ARTICLES --------------------
    suspend fun fetchRandomArticles(continueToken: String? = null): Pair<List<ArticleEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = wikiService.randomArticles(grncontinue = continueToken)

                val newArticles = mutableListOf<ArticleEntity>()
                response.query?.pages?.values?.forEach { page ->
                    val pageId = page.pageid ?: 0
                    val title = page.title ?: ""
                    val snippet = page.revisions?.firstOrNull()?.content
                    newArticles.add(
                        ArticleEntity(
                            pageId = pageId.toLong(),
                            title = title,
                            snippet = snippet,
                            content = snippet ?: "" // or `snippet` if you don't have full content yet
                        )
                    )
                }

                if (newArticles.isNotEmpty()) dao.insertArticles(newArticles)

                val cont = response.continueInfo?.grncontinue
                if (cont != null) dao.insertContinueToken(ContinueTokensEntity("random", cont))

                Pair(newArticles, cont)
            } catch (e: Exception) {
                Log.e("WikiRepo", "error fetchRandomArticles: ${e.message}")
                Pair(dao.getAllArticles(), null) // fallback to local
            }
        }
    }

    // -------------------- FEATURED IMAGES --------------------
    suspend fun fetchFeaturedImages(url: String): Pair<List<ImageEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = commonsService.featuredImages(url)

                val images = mutableListOf<ImageEntity>()
                response.query?.categorymembers?.forEach { member ->
                    // You may want another API call to fetch actual imageinfo, but here we keep it simple
                    images.add(
                        ImageEntity(
                            url = "https://commons.wikimedia.org/wiki/${member.title}",
                            user = null,
                            timestamp = null
                        )
                    )
                }

                if (images.isNotEmpty()) dao.insertImages(images)

                // Commons featuredImages endpoint usually doesn’t use continue, but in case you add it later:
                val cont: String? = null

                Pair(images, cont)
            } catch (e: Exception) {
                Pair(dao.getAllImages(), null)
            }
        }
    }

    // -------------------- CATEGORIES --------------------
    suspend fun fetchCategories(prefix: String = "List of", accontinue: String? = null): Pair<List<CategoryEntity>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = wikiService.categories(acprefix = prefix, accontinue = accontinue)

                val categories = response.query?.allcategories
                    ?.mapNotNull { item -> item.category?.let { CategoryEntity(it) } }
                    ?: emptyList()

                if (categories.isNotEmpty()) dao.insertCategories(categories)

                val cont = response.continueInfo?.accontinue
                if (cont != null) dao.insertContinueToken(ContinueTokensEntity("categories", cont))

                Pair(categories, cont)
            } catch (e: Exception) {
                Pair(dao.getAllCategories(), null)
            }
        }
    }

    // -------------------- LOCAL CACHE HELPERS --------------------
    suspend fun localArticles() = dao.getAllArticles()
    suspend fun localImages() = dao.getAllImages()
    suspend fun localCategories() = dao.getAllCategories()
    suspend fun getContinueToken(endpoint: String) = dao.getContinueToken(endpoint)
}