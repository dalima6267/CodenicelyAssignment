package com.dalima.wikipedia_codenicely_assignment

import com.google.gson.annotations.SerializedName

//data class RandomArticlesResponse(
//    val batchcomplete: Boolean?,
//    val continueInfo: ContinueInfo?,
//    val query: QueryResult?
//)
//
//data class ContinueInfo(
//    @SerializedName("continue")
//    val `continue`: String?,  // backticks allow reserved words
//    @SerializedName("grncontinue")
//    val grncontinue: String?
//)
//
//data class QueryResult(
//    val pages: Map<String, Page>?
//)
//
//data class Page(
//    val pageid: Int?,
//    val ns: Int?,
//    val title: String?,
//    val revisions: List<Revision>?,
//    val images: List<Image>?,
//    val extract: String?,      // <-- add this if using extract endpoint
//    val snippet: String?       // <-- add this if using search endpoint
//)
//
//
//data class Revision(
//    @SerializedName("contentformat") val contentformat: String?,
//    @SerializedName("contentmodel") val contentmodel: String?,
//    @SerializedName("*") val content: String?   // map `*` correctly
//)
//
//
//data class Image(
//    val ns: Int?,
//    val title: String?
//)
//
//
//// -------------------- CATEGORIES --------------------
//data class CategoriesResponse(
//    val batchcomplete: Boolean?,
//    val continueInfo: CategoryContinue?,
//    val query: CategoryQuery?
//)
//
//data class CategoryContinue(
//    val accontinue: String?,
//    @SerializedName("continue")
//    val `continue`: String?
//)
//
//data class CategoryQuery(
//    val allcategories: List<CategoryItem>?
//)
//
//data class CategoryItem(
//    val category: String?
//)
//
//
//// -------------------- FEATURED IMAGES --------------------
//data class FeaturedImagesResponse(
//    val batchcomplete: Boolean?,
//    val query: FeaturedQuery?
//)
//
//data class FeaturedQuery(
//    val categorymembers: List<CategoryMember>?
//)
//
//data class CategoryMember(
//    val pageid: Int?,
//    val ns: Int?,
//    val title: String?
//)
//data class ImageInfo(
//    val url: String?,
//    val user: String?,
//    val timestamp: String?
//)
// Continue block common
data class ContinueBlock(
    @SerializedName("continue") val continueToken: String? = null,
    val grncontinue: String? = null,
    val accontinue: String? = null,
    val gcmcontinue: String? = null
)

// ---------------- Random ----------------
data class RandomArticlesResponse(
    val batchcomplete: Boolean?,
    @SerializedName("continue") val cont: ContinueBlock?,
    val query: RandomQuery?
)

data class RandomQuery(
    val pages: List<RandomPage>? // with formatversion=2 pages is a list
)

data class RandomPage(
    val pageid: Long?,
    val ns: Int?,
    val title: String?,
    val extract: String?, // summary/extract when using extracts
    val thumbnail: Thumbnail?, // page image
    val terms: Terms?,
    val revisions: List<Revision>?
)

data class Thumbnail(val source: String?, val width: Int?, val height: Int?)
data class Terms(val description: List<String>?)
data class Revision(val slots: Map<String, RevisionSlot>?)
data class RevisionSlot(@SerializedName("*") val content: String?)

// ---------------- Categories ----------------
data class CategoriesResponse(
    val batchcomplete: Boolean?,
    @SerializedName("continue") val cont: ContinueBlock?,
    val query: CategoriesQuery?
)
data class CategoriesQuery(val allcategories: List<CategoryItem>?)
data class CategoryItem(val category: String?)

// ---------------- Featured Images (Commons) ----------------
data class FeaturedImagesResponse(
    val batchcomplete: Boolean?,
    @SerializedName("continue") val cont: ContinueBlock?,
    val query: FeaturedQuery?
)
data class FeaturedQuery(val pages: List<FeaturedPage>?)
data class FeaturedPage(
    val pageid: Long?,
    val ns: Int?,
    val title: String?,
    val imageinfo: List<ImageInfo>?
)
data class ImageInfo(val timestamp: String?, val user: String?, val url: String?)