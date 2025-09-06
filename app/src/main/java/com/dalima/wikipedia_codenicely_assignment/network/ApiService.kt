package com.dalima.wikipedia_codenicely_assignment.network

import com.dalima.wikipedia_codenicely_assignment.data.CategoriesResponse
import com.dalima.wikipedia_codenicely_assignment.data.CategoryMembersResponse
import com.dalima.wikipedia_codenicely_assignment.data.FeaturedImagesResponse
import com.dalima.wikipedia_codenicely_assignment.data.RandomArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("w/api.php")
    suspend fun randomArticles(
        @Query("format") format: String = "json",
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "random",
        @Query("grnnamespace") grnnamespace: Int = 0,
        @Query("prop") prop: String = "revisions|images|pageimages|terms|extracts",
        @Query("rvprop") rvprop: String = "content",
        @Query("rvslots") rvslots: String = "main",
        @Query("grnlimit") grnlimit: Int = 10,
        @Query("pithumbsize") pithumbsize: Int = 600, // request thumbnails
        @Query("pilimit") pilimit: Int = 10,
        @Query("exintro") exintro: Boolean = true,
        @Query("explaintext") explaintext: Boolean = true,
        @Query("formatversion") formatversion: Int = 2,
        // continue
        @Query("continue") cont: String? = null,
        @Query("grncontinue") grncontinue: String? = null,
        @Query("excontinue") excontinue: String? = null
    ): RandomArticlesResponse

    @GET("w/api.php")
    suspend fun categories(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("list") list: String = "allcategories",
        @Query("acprefix") acprefix: String,
        @Query("formatversion") formatversion: Int = 2,
        @Query("aclimit") aclimit: Int = 50,
        @Query("continue") cont: String? = null,
        @Query("accontinue") accontinue: String? = null
    ): CategoriesResponse

    @GET("w/api.php")
    suspend fun featuredImages(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("prop") prop: String = "imageinfo",
        @Query("iiprop") iiprop: String = "timestamp|user|url",
        @Query("generator") generator: String = "categorymembers",
        @Query("gcmtype") gcmtype: String = "file",
        @Query("gcmtitle") gcmtitle: String = "Category:Featured_pictures_on_Wikimedia_Commons",
        @Query("gcmlimit") gcmlimit: Int = 50,
        @Query("formatversion") formatversion: Int = 2,
        // continue
        @Query("continue") cont: String? = null,
        @Query("gcmcontinue") gcmcontinue: String? = null
    ): FeaturedImagesResponse


}