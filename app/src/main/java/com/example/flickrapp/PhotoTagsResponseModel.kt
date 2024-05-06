package com.example.newlist.model

import com.google.gson.annotations.SerializedName

data class PhotoTagsResponseModel(
    @SerializedName("photo") val photo: PhotoTags
)

data class PhotoTags(
    @SerializedName("tags") val tags: TagsList
)

data class TagsList(
    @SerializedName("tag") val tag: List<Tag>
)

data class Tag(
    @SerializedName("raw") val raw: String
)