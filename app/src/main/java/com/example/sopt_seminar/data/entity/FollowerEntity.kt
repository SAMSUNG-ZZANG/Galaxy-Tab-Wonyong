package com.example.sopt_seminar.data.entity

import com.example.sopt_seminar.domain.model.Follower
import com.google.gson.annotations.SerializedName

data class FollowerEntity(
    val id: Int,
    val avatar_url: String,
    val login: String,
    val node_id: String,
    val gravatar_id: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
)

fun FollowerEntity.toFollower() = Follower(
    id = id,
    profile = avatar_url,
    name = login,
)