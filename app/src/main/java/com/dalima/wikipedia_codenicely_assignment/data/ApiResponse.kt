package com.dalima.wikipedia_codenicely_assignment.data

import com.google.gson.annotations.SerializedName

data class RandomArticlesResponse(
    val batchcomplete: Boolean?,
    @SerializedName("continue") val cont: ContinueBlock?,
    val query: RandomQuery?
)

data class RandomQuery(
    val pages: List<RandomPage>?
)

data class RandomPage(
    val pageid: Long?,
    val ns: Int?,
    val title: String?,
    val extract: String?,
    val thumbnail: Thumbnail?,
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

data class CategoryMembersResponse(
    val batchcomplete: Boolean?,
    @SerializedName("continue") val cont: ContinueBlock?,
    val query: CategoryMembersQuery?
)

data class CategoryMembersQuery(
    val categorymembers: List<CategoryMember>?
)

data class CategoryMember(
    val pageid: Long?,
    val ns: Int?,
    val title: String?,
    val extract: String?,
    val thumbnail: Thumbnail?
)
data class ContinueBlock(
    @SerializedName("continue")
    val continueToken: String? = null,

    // generator= random
    val grncontinue: String? = null,

    // categories list
    val accontinue: String? = null,

    // commons generator=categorymembers (on commons API) uses gcmcontinue
    val gcmcontinue: String? = null,

    // list=categorymembers (on en.wikipedia) uses cmcontinue
    val cmcontinue: String? = null
)