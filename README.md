# Seminar1 Assignment

### 결과
![ezgif com-gif-maker](https://user-images.githubusercontent.com/82709044/162190180-e48ff3b6-f9fc-4f4e-8dbf-fbab8d1d4704.gif)

## Stack

- MVVM
- DataBinding
- DI
- DataStore
- Navigation

## 구현 기능

### SignInActivity

1. 아이디, 비밀번호 모두 입력 후 로그인 버튼을 눌렀을 때 로그인 성공시 토스트 메시지를 띄운다.
2. 하나라도 비었으면 아이디/비밀번호를 확인해주세요 라는 토스트 메시지를 띄운다.
3. 회원가입 버튼 누를 시 SignUpActivity 이동
4. 로그인 버튼을 누를 시 Local 저장소에 저장된 아이디와 비밀번호와 입력한 아이디와 비밀번호가 같다면 HomeActivity로 이동한다. 만약 같지 않다면 토스트 메시지를 띄운다.

### SignUpActivity

1. 이름, 아이디, 비밀번호중 하나라도 입력하지 않으면 토스트 메시지를 띄운다.
2. 회원가입 완료를 누르면 유효성 검사를 통해 이름, 아이디, 비밀번호가 올바르게 입력했는지 확인하고 재대로 입력되지 않았으면 토스트 메시지를 띄운다.
3. 재대로 입력했으면 아이디, 비밀번호를 Local 저장소에 저장한 후, 아이디, 비밀번호가 로그인 화면으로 전달한다.

## 구현 방법
### 1. 화면 전환의 흐름을 직관적으로 볼 수 있고, safeArgs 통해서 화면 간의 데이터를 안전하게 전달할 수 있다는 장점이 있다고 들어서 Intent가 아닌 Navigation을 이용해서 화면 전환과 화면간의 데이터 전달을 했다.

#### navigation graph

![image](https://user-images.githubusercontent.com/82709044/162136968-08ad2928-ccd9-4e6d-b847-d44fdef35e5d.png)

#### action

```xml

        <action
            android:id="@+id/action_sign_up_fragment_to_sign_in_fragment"
            app:destination="@id/sign_in_fragment"
            app:popUpTo="@id/nav_graph"
            app:popUpToInclusive="true"
            >
            <argument
                android:name="userId"
                app:argType="string"
                android:defaultValue="" />
            <argument
                android:name="userPassword"
                app:argType="string"
                android:defaultValue="" />
        </action>

```

- navigation action 내부에 argument를 추가해서 다른 화면에 데이터를 전달할 수 있다.
- popUpTo, popUpToInclusive 를 통해서 회원가입 화면에서 로그인 화면으로 전환한 후에 회원가입 화면을 백스택에 추가하지 않을 수 있다.

```kotlin
val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                    userId = id,
                    userPassword = password
                )
                findNavController().navigate(action)
```
- 로그인 화면으로 돌아갈 때, 회원가입 때 입력했던 정보를 전달한다.

```xml
<fragment
        android:id="@+id/sign_in_fragment"
        android:name="com.example.sopt_seminar.ui.SignInFragment"
        android:label="sign_in_activity"
        tools:layout="@layout/sign_in_activity" >
        <argument
            android:name="userId"
            app:argType="string"
            android:defaultValue="" />
        <argument
            android:name="userPassword"
            app:argType="string"
            android:defaultValue="" />
        <action
            android:id="@+id/action_sign_in_fragment_to_sign_up_fragment"
            app:destination="@id/sign_up_fragment" />
        <action
            android:id="@+id/action_sign_in_fragment_to_home_fragment"
            app:destination="@id/home_fragment" />
    </fragment>
```

- action 밖에 argument를 추가해서 전달받을 데이터를 생성할 수 있다.

```kotlin
private val args: SignInFragmentArgs by navArgs()
with(binding) {
            // id, password는 databinding variable 변수 이름 
            id = args.userId
            password = args.userPassword
}
```

- navArgs()를 통해서 데이터를 전달받을 수 있다.

### 2. UI, Data 등 패키지를 만들어서 파일을 구분함으로써 CleanArchitecture+MVVM을 적용했다.
    
![image](https://user-images.githubusercontent.com/82709044/162137076-2ca2a340-54b2-4500-97e9-b817c435b651.png)
    
### 3. 파일의 파라미터로 객체가 있다면 의존성 주입을 통해서 객체를 주입했다.
    
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserLocalDataSource(@ApplicationContext context: Context): UserLocalDatSource {
        return UserLocalDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userLocalDatSource:UserLocalDatSource): UserRepository {
        return UserRepositoryImpl(userLocalDatSource)
    }
}
```
 
### 4. DataStore를 이용해서 내부저장소에 아이디, 비밀번호를 저장했다.
    
![image](https://user-images.githubusercontent.com/82709044/162137175-70dc82ef-6d57-4c22-904e-60321c1b6a4f.png)

위 표와 같은 이유로 SharedPreferences보다 PreferencesDatastore 사용을 권장해서 Datastore를 이용해서 구현했다.

#### 데이터 저장

```kotlin
class UserLocalDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : UserLocalDatSource {
    private val Context.dataStore by preferencesDataStore(name = DATASTORE)
    private val userIdKey = stringPreferencesKey(GET_USER_ID)
    private val userPasswordKey = stringPreferencesKey(GET_USER_PASSWORD)

    override suspend fun setUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[userIdKey] = user.userId
            preferences[userPasswordKey] = user.userPassword
        }
    }
}

data class User(
    val userId: String = "",
    val userPassword: String = "",
)
```

#### 데이터 불러오기

```kotlin
private val _user = MutableLiveData(User())
    private val user: LiveData<User> = _user

    suspend fun checkInput(idText: String, passwordText: String): Int {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _user.value = it
            }
        }
        delay(100)
        return answer
    }
```

- delay(100)을 한 이유
  - viewModelScope.launch로 인해서 userRepository.getUser()와 return 이 동시에 진행되는데 getUser()의 연산이 끝나지도 않았는데 return이 되기 때문에 delay(100)을 통해서 return 의 호출을 지연한다.

### 5. DataBinding으로 양방향 데이터 결합을 했다.
- DataBinding : 뷰와 상호작용하는 코드를 쉽게 작성할 수 있다.
- ViewBinding : 뷰와 상호작용하는 코드뿐만 아니라 xml에서 표현식 언어로 레이아웃의 뷰와 변수를 연결할 수 있다.

#### XML
```xml
<data>
        <variable
            name="activity"
            type="com.example.sopt_seminar.ui.SignInFragment" />
        <variable
            name="id"
            type="String" />
        <variable
            name="password"
            type="String" />
</data>
```

```xml
android:text="@{id}"
android:text="@{password}"
android:onClick="@{()->activity.goMainActivity()}"
```

#### Activity
```kotlin
binding.activity = this@SignInFragment
binding.id = args.userId
binding.password = args.userPassword
fun goMainActivity() {
        findNavController().navigate(R.id.sign_up_fragment)
}
```

### 배운 내용
- Navigation 화면전환 후 백스택 안들어가게 만들기
- DataBinding 양방향 데이터 결합하기
- DataStore 사용법
