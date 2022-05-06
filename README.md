# Seminar3 Assignment
![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/82709044/167149700-fae41894-3b10-4464-a458-7425f8a6e6df.gif)

### 폰트 적용하기

```xml
<style name="custom_text" parent="android:Widget.TextView">
    <item name="android:textColor">@color/black</item>
    <item name="android:fontFamily">@font/inconsolata</item>
</style>
```

Text Style 안에 font와 color를 설정해서 TextView 마다 적용했다.

### Button Selector

Button이 선택될 때와 선택되지 않았을 때 컬러를 구분했다.

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/white" />
            <stroke android:width="1dp" android:color="@color/black" />
        </shape>
    </item>

    <item android:state_selected="false">
        <shape android:shape="rectangle">
            <solid android:color="@color/not_focus" />
            <stroke android:width="1dp" android:color="@color/not_focus" />
        </shape>
    </item>
</selector>
```

버튼을 선택하면 해당 버튼의 id를 구분한 후, 나머지 버튼의 isSelected를 false로 바꿔 준다.

```kotlin
/* ProfileFragment */
val changeState = fun(view: View) {
    val followerBtn = binding.followerBtn
    val repoBtn = binding.repoBtn
    when (view.id) {
        R.id.follower_btn -> {
            if (!followerBtn.isSelected) {
                followerBtn.isSelected = true
                repoBtn.isSelected = false
            }
        }
        R.id.repo_btn -> {
            if (!repoBtn.isSelected) {
                repoBtn.isSelected = true
                followerBtn.isSelected = false
            }
        }
    }
}
```

버튼을 클릭했을 때 해당 버튼의 view를 changeState 고차 함수 안에 넣어줘서 선택 효과를 준다.

```kotlin
@JvmStatic
@BindingAdapter(value = ["fragmentManger", "childFragment", "changeSelectState"])
fun changeFragment(
  button: Button,
  fragmentManger: FragmentManager,
  childFragment: Fragment,
  changeSelectState: (View) -> Unit
) {
  button.setOnClickListener {
      fragmentManger.beginTransaction().replace(R.id.home_fragment_cv, childFragment).commit()
      changeSelectState(button)
  }
}
```

### Bottom NavigationBar/TabLayout  + ViewPager2

2주차 세미나 자료 참조해서 구현했다.

### Glide

```kotlin
@JvmStatic
@BindingAdapter("setDrawable")
fun showImg(imageView: ImageView, drawable: Int) {
    Glide.with(imageView.context)
        .load(drawable)
        .override(100, 100)
        .circleCrop()
        .error(R.drawable.ic_baseline_person_24)
        .into(imageView)
}
```

DataBinding을 통해서 drawable id를 받고, Glide를 이용해서 이미지를 원형으로 표시했다.

### ViewPager2 중첩 스크롤 문제 해결

[NestedScrollableHost](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt) 해당 클래스를 만들고, 자식 viewPager에 감싸준다.

```xml
<com.example.sopt_seminar.util.NestedScrollableHost
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/home_tl">

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/home_vp"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</com.example.sopt_seminar.util.NestedScrollableHost>
```
