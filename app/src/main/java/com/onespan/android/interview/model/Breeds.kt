package com.onespan.android.interview.model

import com.google.gson.annotations.SerializedName

data class Breeds(
    @SerializedName("current_page")
    var currentPage: Int = 0,

    @SerializedName("data")
    var breeds: MutableList<Breed> = mutableListOf(),

    @SerializedName("first_page_url")
    var firstPageUrl : String? = null,

    var from: Int? = null,

    @SerializedName("last_page")
    var lastPage : Int? = null,

    @SerializedName("last_page_url")
    var lastPageUrl : String? = null,

    var links: MutableList<Link> = mutableListOf(),

    @SerializedName("next_page_url")
    var nextPageUrl: String? = null,

    var path: String? = null,

    @SerializedName("per_page")
    var perPage : String? = null,

    @SerializedName("prev_page_url")
    var prevPageUrl: String? = null,

    var to : Int? = null,
    var total: Int? = null
)
