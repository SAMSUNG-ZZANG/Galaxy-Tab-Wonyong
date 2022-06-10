# Seminar7 Assignment
![ezgif com-gif-maker](https://user-images.githubusercontent.com/82709044/173099004-5eeb2d9d-6fe2-4b1f-81ba-317c823228d3.gif)
## NavGraph

한 navGraph 안에 온보딩 화면까지 넣으면 번잡해보일까봐 자동로그인일 때 보여줄 `main_nav_graph.xml`, 자동로그인이 아닐 때 보여줄 `login_nav_graph.xml`로 나눴다.

**login_nav_graph**

![image](https://user-images.githubusercontent.com/82709044/173099569-dfbd34dd-fa0c-4181-9829-a101597176ad.png)

**main_nav_graph**

![image](https://user-images.githubusercontent.com/82709044/173099622-f4282bf8-5bb2-4ff3-981b-6d0ad0ec06ea.png)

## 자동로그인

1. 로그인 화면에서 자동로그인 체크박스를 체크하고 로그인을 하면 dataStore에 true 값을 저장한다.
2. 메인 화면에서 설정 버튼을 누른 후, 자동로그인 해제 아이콘을 누르면 나오는 Dialog에서 해제 버튼을 누르면 dataStore에 false 값을 저장한다
3. Splash Screen이 나올 때 MainActivity에서 dataStore의 값을 확인하고 true면 main화면으로, false이면 로그인 화면으로 이동한다
    
    ```kotlin
    lifecycleScope.launch {
    	  isAutoLoginUseCase().collect { isAutoLogin ->
    	      if (isAutoLogin) findNavController(R.id.nav_host_fragment).setGraph(R.navigation.main_nav_graph)
    	      else findNavController(R.id.nav_host_fragment).setGraph(R.navigation.login_nav_graph)
    	  }
    }
    ```
    

## Splash Screen

Android 12부터 Splash Screen API를 지원해준다길래 한번 사용해봤다

1. dependency를 추가한다

```kotlin
// Splash Screen
implementation 'androidx.core:core-splashscreen:1.0.0-rc01'
```

1. `themes.xml` 파일에 아래와 같이 설정한다

```xml
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenBackground">@color/white</item>
    <item name="windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
    <!-- Required for animated icons -->
    <item name="windowSplashScreenAnimationDuration">1000</item>
    <item name="postSplashScreenTheme">@style/Theme.SoptSeminar</item>
</style>
```

1. manifest에 설정한 theme를 선언한다

```xml
<application
	...
	android:theme="@style/Theme.App.Starting"
```

1. mainActivity에 splash screen을 호출한다

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	installSplashScreen()
	setContentView(binding.root)
}
```

이 때 installSplashScreen()을 setContentView 선언 전에 호출해야 한다

안그러면  `java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity` 에러가 생긴다

구글문서에 나온대로 그대로 한번 해봤는데, Splash Activity를 따로 안 만들어서 괜찮은 것 같다.

### 궁금한 점
```kotlin
lifecycleScope.launch {
	  isAutoLoginUseCase().collect { isAutoLogin ->
	      if (isAutoLogin) findNavController(R.id.nav_host_fragment).setGraph(R.navigation.main_nav_graph)
	      else findNavController(R.id.nav_host_fragment).setGraph(R.navigation.login_nav_graph)
	  }
}
```
위 로직은 Splash Screen이 나올 때 MainActivity에서 자동로그인 체크를 위한 로직입니다.

영상을 보면 diolog에서 자동로그인 해제를 누르자마자 바로 로그인 화면으로 돌아가는 것을 볼 수 있습니다.

원했던 화면 흐름이긴 했지만, dataStore에서 false가 되자마자 바로 위 로직을 통해서 graph를 바꿨다는 것인데

**이런 상황도 메모리 누수인지 궁금합니다**

왜냐면 의도한 것은 Splash Screen에서 자동로그인 체크를 단 한번하는 것인데,

위와 같은 상황은 계속 flow로 데이터를 수집하고 있다는 것이기 때문입니다

메모리누수인가요???

--------
온보딩 화면은 fragment 3개 만들기 싫어서, fragment 1개로 어떻게 할 수 있을까 고민하다가 시험기간 때 안 봤던 유투브 정주행했더니 시간이 이렇게 됐네요.. 추후에 올리겠습니다..!

[스플래쉬 스크린](https://developer.android.com/guide/topics/ui/splash-screen)

[스플래쉬 마이그레이션](https://developer.android.com/guide/topics/ui/splash-screen/migrate)
