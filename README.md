# Seminar4 Assignment
![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/82709044/168252716-fb84cf21-74f8-4364-b20f-0ce2c4c2b7e4.gif)

## Response wrapper class

```kotlin
/* sign up response data */
{
  "status": 0,
  "message": "string",
  "data": {
    "id": 0
  }
}
```

```kotlin
/* sign in response data */
{
  "status": 0,
  "message": "string",
  "data": {
    "email": "string",
    "name": "string"
  }
}
```

```kotlin
data class CommonResponse<T>(
    val status: Int,
    val message: String,
    val success: String,
    val data: T
)
```

```kotlin
sealed class DataResponse {
    data class SignIn(
        val email: String,
        val name: String,
    ) : DataResponse()

    data class SignUp(
        val id: Int
    ) : DataResponse()
}
```

```kotlin
@POST(SIGN_UP)
suspend fun signUp(
  @Body signUpRequest: SignUpRequest
): Response<CommonResponse<DataResponse.SignUp>>

@POST(SIGN_IN)
suspend fun signIn(
  @Body signInRequest: SignInRequest
): Response<CommonResponse<DataResponse.SignIn>>
```

SignUp, SignIn 반환 값을 보면 data를 제외한 나머지 반환 값들이 같은 것을 볼 수 있다.

그래서 `CommonResponse` 내에 있는 data를 제네릭으로 만들고, `sealed class`를 만들어서 **SignIn 전용 data class**, **SignUp 전용 data class**를 만들어서 `CommonResponse` 내에 넣어줬다.

## Error Response

```kotlin
/* error response */
"data": {
  "status": 409,
  "success": false,
  "message": "Duplicate",
},
```

```kotlin
data class ErrorResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
)
```

```kotlin
private val gson = Gson()
private val type = object : TypeToken<ErrorResponse>() {}.type
val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
```

통신 작업을 실패했을 때 토스트 메시지를 호출하기 위해서 `errorBody().toString()` 를 메시지로 넣었지만 이상한 문자들만 나왔다.

그래서 검색해보니 `errorBody()`를 변환해줘야 한다해서 `ErrorResponse`를 만들어서 `errobody()` 내용을 `string`으로 변환해서 에러 내용을 토스트 메시지로 호출했다

## Github 연동

```json
[
  {
    "login": "octocat",
    "id": 1,
    "node_id": "MDQ6VXNlcjE=",
    "avatar_url": "https://github.com/images/error/octocat_happy.gif",
    "gravatar_id": "",
    "url": "https://api.github.com/users/octocat",
    "html_url": "https://github.com/octocat",
    "followers_url": "https://api.github.com/users/octocat/followers",
    "following_url": "https://api.github.com/users/octocat/following{/other_user}",
    "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
    "organizations_url": "https://api.github.com/users/octocat/orgs",
    "repos_url": "https://api.github.com/users/octocat/repos",
    "events_url": "https://api.github.com/users/octocat/events{/privacy}",
    "received_events_url": "https://api.github.com/users/octocat/received_events",
    "type": "User",
    "site_admin": false
  }
]
```

```kotlin
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
    profile = avatar_url,
    name = login,
)
```

```kotlin
override suspend fun getFollowerList(): Result {
        val response = userRemoteDataSource.getFollowerList()

        return if (response.isSuccessful) {
            val followerList: List<Follower> = response.body()?.map { followerEntity ->
                followerEntity.toFollower()
            }!!
            Result.Success(followerList)
        } else {
            Result.Fail(response.errorBody()?.string())
        }
    }
```
변수 네이밍이 카멜케이스가 아닌 변수만 @SerializedName를 사용했다.
객체들 값이 무엇을 표현하는지 몰라서 entity로 일단 다받아서 로그로 확인한 후, Recyclerview에 보여줄 값들만 매핑시켜주는 확장함수를 만들어서 `FollowerEntity` 에서 `Follower`로 변환했다.

## Coroutine

`fun` 을 통해 서버 통신을 한다면 **UI스레드**에서 통신을 하기 때문에 통신이 끝날 때까지 **UI스레드**는 대기하거나 차단된다. **UI스레드**가 차단되는 경우 애플리케이션이 응답 없음(ANR) 대화상자가 표시될 수 있다.

위 문제를 해결하기 위해서 `withContext` 와 `suspend`를 통해서 통신 작업을 **UI스레드**에서 **I/O스레드**에서 작업하게 만들면 해결된다.

하지만 `Retrofit2` 라이브러리는 내부에 `Dispathcher.IO` 를 가지고 있어서 `withContext(Dispatcher.IO)` 를 안해줘도 **I/O스레드**에서 실행할 수 있다.

또, `Coroutine`을 사용하면 `enqueue callback` 이 필요없어지기 때문에 비동기 방식으로 통신을 할 때 `Call` 대신 `Response`를 사용하는 것이 좋다.

`enqueue callback` : 실제 서버통신을 비동기적으로 요청하는 동작을 한다.

```kotlin
/* example */
@POST(SIGN_IN)
suspend fun signIn(
    @Body signInRequest: SignInRequest
): Response<CommonResponse<DataResponse.SignIn>>

/* example */
override suspend fun signIn(userEmail: String, userPassword: String): Result {
    val response = userRemoteDataSource.signInUser(userEmail, userPassword)

    return if (response.isSuccessful) {
        val msg = "성공 ${response.body()?.message} 코드: ${response.body()?.status}"
        Result.Success(msg)
    } else {
        val errorResponse: ErrorResponse? =
            gson.fromJson(response.errorBody()!!.charStream(), type)
        val msg = "실패 ${errorResponse?.message} 코드: ${errorResponse?.status}"
        Result.Fail(msg)
    }
}
```

### 궁금한 점 1
```kotlin
/* LOGIN API */
const val BASE_URL = "http://13.124.62.236"
const val SIGN_UP = "$BASE_URL/auth/signup"
const val SIGN_IN = "$BASE_URL/auth/signin"

/* GITHUB API */
const val GITHUB_URL = "https://api.github.com"
const val GITHUB_USER_FOLLOWERS = "$GITHUB_URL/users/{username}/followers"

/* Retrofit Builder */
@Provides
fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

@Provides
fun provideAuthApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

/* ApiService */
@POST(SIGN_IN)
suspend fun signIn(
    @Body signInRequest: SignInRequest
): Response<CommonResponse<DataResponse.SignIn>>

@GET(GITHUB_USER_FOLLOWERS)
suspend fun getFollowerList(
    @Path("username") userName: String = "KWY0218",
): Response<List<FollowerEntity>>
```

까먹고 github `retrofit builder`를 따로 안 만들고 baseUrl이 `BASE_URL`인 `retrofit builder`로

github 팔로워 리스트를 가져오는 통신을 했는데 통신이 됐습니다.

이게 왜 되는지 모르겠습니다.


[구글 문서](https://developer.android.com/kotlin/coroutines)

[Will I always add withContext(Dispatchers.IO) in suspend when I pull data from a remote server?](https://stackoverflow.com/questions/60911310/will-i-always-add-withcontextdispatchers-io-in-suspend-when-i-pull-data-from-a)

[Call or Response in Retrofit?](https://stackoverflow.com/questions/64124670/call-or-response-in-retrofit)

[이벤트 처리(ViewModel에서 Activity로 toast 메시지 전송)](https://medium.com/prnd/mvvm%EC%9D%98-viewmodel%EC%97%90%EC%84%9C-%EC%9D%B4%EB%B2%A4%ED%8A%B8%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-6%EA%B0%80%EC%A7%80-31bb183a88ce)
