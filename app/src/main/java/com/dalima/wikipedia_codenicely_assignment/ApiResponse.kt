package com.dalima.wikipedia_codenicely_assignment

import com.google.gson.annotations.SerializedName

data class RandomArticlesResponse(
    val batchcomplete: Boolean?,
    val continueInfo: ContinueInfo?,
    val query: QueryResult?
)

data class ContinueInfo(
    @SerializedName("continue")
    val `continue`: String?,  // backticks allow reserved words
    @SerializedName("grncontinue")
    val grncontinue: String?
)

data class QueryResult(
    val pages: Map<String, Page>?
)

data class Page(
    val pageid: Int?,
    val ns: Int?,
    val title: String?,
    val revisions: List<Revision>?,
    val images: List<Image>?
)

data class Revision(
    val contentformat: String?,
    val contentmodel: String?,
    val content: String?
)

data class Image(
    val ns: Int?,
    val title: String?
)


// -------------------- CATEGORIES --------------------
data class CategoriesResponse(
    val batchcomplete: Boolean?,
    val continueInfo: CategoryContinue?,
    val query: CategoryQuery?
)

data class CategoryContinue(
    val accontinue: String?,
    @SerializedName("continue")
    val `continue`: String?
)

data class CategoryQuery(
    val allcategories: List<CategoryItem>?
)

data class CategoryItem(
    val category: String?
)


// -------------------- FEATURED IMAGES --------------------
data class FeaturedImagesResponse(
    val batchcomplete: Boolean?,
    val query: FeaturedQuery?
)

data class FeaturedQuery(
    val categorymembers: List<CategoryMember>?
)

data class CategoryMember(
    val pageid: Int?,
    val ns: Int?,
    val title: String?
)
