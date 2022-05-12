package com.example.sopt_seminar.data.constants

const val DATASTORE = "DATASTORE"
const val GET_USER_ID = "GET_USER_ID"
const val GET_USER_PASSWORD = "GET_USER_PASSWORD"
const val GET_USER_NAME = "GET_USER_NAME"

/* LOGIN API */
const val BASE_URL = "http://13.124.62.236"
const val SIGN_UP = "$BASE_URL/auth/signup"
const val SIGN_IN = "$BASE_URL/auth/signin"

/* GITHUB API */
const val GITHUB_URL = "https://api.github.com"
const val GITHUB_HEADERS = "application/vnd.github.v3+json"
const val GITHUB_USER_FOLLOWERS = "$GITHUB_URL/users/{username}/followers"