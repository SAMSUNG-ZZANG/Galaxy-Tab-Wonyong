package com.example.sopt_seminar.data.entity

import com.example.sopt_seminar.domain.model.Follower
import com.google.gson.annotations.SerializedName

data class FollowerEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("login") val login: String,
    @SerializedName("node_id") val node_id: String,
    @SerializedName("gravatar_id") val gravatar_id: String,
    @SerializedName("followers_url") val followers_url: String,
    @SerializedName("following_url") val following_url: String,
    @SerializedName("gists_url") val gists_url: String,
    @SerializedName("starred_url") val starred_url: String,
    @SerializedName("organizations_url") val organizations_url: String,
    @SerializedName("repos_url") val repos_url: String,
    @SerializedName("events_url") val events_url: String,
    @SerializedName("received_events_url") val received_events_url: String,
)

fun FollowerEntity.toFollower() = Follower(
    id = id,
    profile = avatar_url,
    name = login,
)