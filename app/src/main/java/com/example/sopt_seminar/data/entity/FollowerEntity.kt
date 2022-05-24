package com.example.sopt_seminar.data.entity

import com.example.sopt_seminar.domain.model.Follower
import com.google.gson.annotations.SerializedName

data class FollowerEntity(
    val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    val login: String,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String,
    @SerializedName("gists_url") val gistsUrl: String,
    @SerializedName("starred_url") val starredUrl: String,
    @SerializedName("organizations_url") val organizationsUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url") val eventsUrl: String,
    @SerializedName("received_events_url") val receivedEventsUrl: String,
)

fun FollowerEntity.toFollower() = Follower(
    id = id,
    profile = avatarUrl,
    name = login,
)