package com.example.dicodingevents.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseEvents(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("listEvents")
    val listEvents: List<ListEventsItem>,
)

data class ResponseEventsDetail(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("event")
    val event: ListEventsItem,
)

data class ListEventsItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("imageLogo")
    val imageLogo: String? = null,

    @field:SerializedName("mediaCover")
    val mediaCover: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("ownerName")
    val ownerName: String? = null,

    @field:SerializedName("cityName")
    val cityName: String? = null,

    @field:SerializedName("quota")
    val quota: Int = 0,

    @field:SerializedName("registrants")
    val registrants: Int = 0,

    @field:SerializedName("beginTime")
    val beginTime: String? = null,

    @field:SerializedName("endTime")
    val endTime: String? = null,

    @field:SerializedName("link")
    val link: String? = null,


    )
