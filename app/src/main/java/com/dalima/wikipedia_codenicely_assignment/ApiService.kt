package com.dalima.wikipedia_codenicely_assignment

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiService {

    // Featured images from Commons
    @GET
    suspend fun featuredImages(@Url url: String): FeaturedImagesResponse

    // Random articles
    @GET("api.php")
    suspend fun randomArticles(
        @Query("format") format: String = "json",
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "random",
        @Query("grnnamespace") grnnamespace: Int = 0,
        @Query("prop") prop: String = "revisions|images",
        @Query("rvprop") rvprop: String = "content",
        @Query("rvslots") rvslots: String = "main",
        @Query("grnlimit") grnlimit: Int = 10,
        @Query("continue") cont: String? = null,
        @Query("grncontinue") grncontinue: String? = null
    ): RandomArticlesResponse

    // All categories
    @GET("api.php")
    suspend fun categories(
        @Query("action") action: String = "query",
        @Query("list") list: String = "allcategories",
        @Query("acprefix") acprefix: String,
        @Query("format") format: String = "json",
        @Query("formatversion") formatversion: Int = 2,
        @Query("aclimit") aclimit: Int = 50,
        @Query("accontinue") accontinue: String? = null
    ): CategoriesResponse
}
