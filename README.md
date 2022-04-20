# Seminar2 Assignment
![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/82709044/164247126-bec4d985-e1b3-435b-bfb6-84ff139d1fc2.gif)

# HomeFragment
- navigation 추가해서 HomeFragment에서 Recyclerview 내에 있는 아이템 중 하나를 클릭하면 HomeFragment → DetailFragment로 이동한다.
- 아이템의 정보를 전달하기 위해서 HomeFragment → DetailFragment로 이동하는 action에 팔로워 이름과 설명을 담는 argument를 추가하고,  DetailFragment 내에 팔로워 이름과 설명을 담는 argument를 추가했다.
![s1](https://user-images.githubusercontent.com/82709044/164221942-e19b8a5d-d36b-45ad-89c1-eaa2a90bb0d5.JPG)
    
    ```kotlin
    <fragment
            android:id="@+id/home_fragment"
            android:name="com.example.sopt_seminar.ui.HomeFragment"
            android:label="home_activty"
            tools:layout="@layout/home_fragment">
            <action
                android:id="@+id/action_home_fragment_to_detailFragment"
                app:destination="@id/detailFragment">
                <argument
                    android:name="followerName"
                    android:defaultValue=""
                    app:argType="string" />
                <argument
                    android:name="followerDes"
                    android:defaultValue=""
                    app:argType="string" />
            </action>
        </fragment>
    
        <fragment
            android:id="@+id/detailFragment"
            android:name="com.example.sopt_seminar.ui.DetailFragment"
            android:label="DetailFragment"
            tools:layout="@layout/detail_fragment">
            <argument
                android:name="followerName"
                android:defaultValue=""
                app:argType="string" />
            <argument
                android:name="followerDes"
                android:defaultValue=""
                app:argType="string" />
        </fragment>
    ```
    
### add가 아닌 replace를 사용해서 FragmentContainerView에 디폴트 값을 FollowerFragment로 설정했다.

<details>
<summary> add -> replace로 한 이유 (삽질) </summary>
<div markdown="1">

> 상황
> 

![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/82709044/164224036-762a6a4c-e9ad-4588-b2a4-1587629abd0c.gif)
        
FollowerFragment → RepoFragment 로 이동할 땐 리사이클러 뷰 내에 있는 데이터가 중첩 되지 않지만

FollowerFragment → DetailFtagment 이동 후 다시 FollowerFragment로 돌아오면 리사이클러 뷰 내 데이터가 중첩되는 현상이 나왔다. 이 상황에서 다시 DetailFragment로 이동했다가 돌아오면 FollowerFragment가 3개 중첩이 된다

하지만 중첩된 후 repository를 갔다가 오면 정상적으로 작동한다

그래서 2개 상황의 생명주기 차이를 확인했다

> FollowerFragment → RepoFollowerFragment
> 

![FollowrToRepoToFollower](https://user-images.githubusercontent.com/82709044/164222596-e37538dc-774e-4d43-aa79-59e9720b6381.JPG)

평범한 Fragment 생명주기를 보여준다.

> FollowerFragment → DetailFragment→ FollowerFragment

![followerToDetailBack](https://user-images.githubusercontent.com/82709044/164222524-fc4078a3-d54f-4b6e-b8e0-4e40d81f6524.JPG)

onDestory가 작동을 안해서 view가 쌓이는 것을 확인했다.

> FollowerFragment → DetailFragment→ FollowerFragment → DetailFragment→ FollowerFragment

![three](https://user-images.githubusercontent.com/82709044/164222479-ce9f2d95-a174-44a0-9995-191621193f80.JPG)

detailFragment를 왕복하면 할수록 계속 fragment가 쌓인다

> 해결 (add→replace)
> 

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().add(R.id.home_fragment_cv, followerFragment)
            .commit()
    }
```

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().replace(R.id.home_fragment_cv, followerFragment)
            .commit()
    }
```

원인이 FollowerFragment인 줄 알고 stackoverflow 찾고, FollowerFragment 생명주기에 `savedInstanceState` 가 들어가는 값에 전부 null을 넣는 등 별 짓을 다했지만 원인은 HomeFragment에 있었다.

초기 default 프래그먼트를 넣기 위해서 add를 해야 되는줄 알아서 add를 한 것이 원인이었다.

detail을 왔다 갔다 하는 동안 add가 계속 쌓여서 생긴 오류였다.

- replace, add 차이
    
    replace → 이전 프래그먼트를 삭제하고 그 위에 새 프래그먼트를 삽입
    
    add → 제거하지 않고 프래그먼트 삽입
    
요거 해결하려고 4시간은 삽질한거 같다...
</div>
</details>

### navigation을 사용중이라서 frament 안에서 fragment를 설정해야 되서 supportFragmentManager 가아닌 childFragmentManager를 사용했다.

![image](https://user-images.githubusercontent.com/82709044/164221813-041d52d7-6c9b-460d-a0a8-d28190746458.png)
        
```kotlin
childFragmentManager.beginTransaction().replace(R.id.home_fragment_cv, followerFragment)
        .commit()
```
- Tablayout 사용 해서 Follower, Repo Recyclerview 구분했다
- 텝을 변경할 때 마다 FragmentContainerView 내의 Fragment를 replace 했다.

# RepoFragement, FollowerFragment

```kotlin
val simpleCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val startPosition = viewHolder.adapterPosition
            val endPosition = target.adapterPosition

            Collections.swap(testList, startPosition, endPosition)
            adapter.moveItem(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    testList.removeAt(position)
                    adapter.removeItem(position)
                }
            }
        }
    }

ItemTouchHelper(simpleCallback).attachToRecyclerView(followerRecyclerView)
```

- Drag And Drop , Swipe To Dismiss 기능 구현

> 출처
> 
> - swipe to dismiss
>     
>     [https://www.youtube.com/watch?v=Aup-aPj24eU](https://www.youtube.com/watch?v=Aup-aPj24eU)
>     
> - drag and drop
>     
>     [https://www.youtube.com/watch?v=TXAbYWZhpBQ](https://www.youtube.com/watch?v=TXAbYWZhpBQ)
>     
- LayoutManager
- FollowerFragment ← `followerRecyclerView.layoutManager = LinearLayoutManager(context)`  열이 1개인  recyclerview
- RepoFragment ← `repoRecyclerView.layoutManager = GridLayoutManager(context, 2)` 열이 2개인 recyclerview
- BaseFragment를 만들어서 Fragment를 생성할 때마다 반복적인 코드를 줄일 수 있다.
- `class FollowerFragment : BaseFragment<FollowerFragmentBinding>(R.layout.follower_fragment)`
- 이런식으로 Fragment를 만들 때마다 상속하면 된다.

#### 주의할점
`adapter.submitList(testList)` 이런식으로 원본 객체를 전달하면 diffutill에서 계산할 때 오류가 생긴다.
`adapter.submitList(testList.toCollection(mutableListOf())` 복사본을 전달해서 오류를 해결했다.

# Repo, Follower Adapter
- ListAdapter로 구현
- ListAdapter는 Recyclerview Adapter가 기반인 클래스이고 아이템들 사이의 차이점을 백스라운드에서 계산을 한다.
- 기존 recyclerview adapter에서 데이터가 변경되면 notifyDataSetChaned 연산을 통해서 데이터의 변경을 적용했다. 하지만 notifyDataSetChaned 연산은 변경되지 않은 데이터를 포함해서 전체를 갈아치워서 데이터의 크기가 크다면 지연되는 일이 발생한다.
- 이를 해결하기 위해서 DiffUtil을 사용한다. DiffUtil은 현재 데이터 리스트와 교체될 데이터 리스트를 비교한 후 교체되는 데이터만을 바꿔줘서 notifyDataSetChaned 보다 빠르게 연산이 가능하다.
- 그래서 데이터 값이 변경될 때 submitList로 변경된 리스트를 보내면 더 빠르게 바뀐 내용을 적용할 수 있다.
- remove, 위치 변경 효과

```kotlin
// Adapter
fun moveItem(fromPosition: Int, toPosition: Int) {
    val newList = currentList.toMutableList()
    Collections.swap(newList, fromPosition, toPosition)
    submitList(newList)
}

fun removeItem(position:Int){
    val newList = currentList.toMutableList()
    newList.removeAt(position)
    submitList(newList)
}
```

> 출처
[https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)
[https://cliearl.github.io/posts/android/recyclerview-listadapter/](https://cliearl.github.io/posts/android/recyclerview-listadapter/)
[https://velog.io/@l2hyunwoo/Android-RecyclerView-DiffUtil-ListAdapter](https://velog.io/@l2hyunwoo/Android-RecyclerView-DiffUtil-ListAdapter)
> 
- 아이템 터치 이벤트

```kotlin
// Adapter
private lateinit var itemClick: OnItemClickListener
interface OnItemClickListener{
    fun onItemClick(position: Int)
}

fun setOnItemClickListener(listener: OnItemClickListener){
    itemClick = listener
}

class RepoViewHolder(binding: RepoFrameBinding,listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
    private val root = binding.repoFrame
    init {
        root.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}

// Fragment
adapter.setOnItemClickListener(object : FollowerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    followerName = testList[position].name,
                    followerDes = testList[position].description
                )
                findNavController().navigate(action)
            }
        })
```

> 출처
> 
> 
> [https://www.youtube.com/watch?v=dB9JOsVx-yY&t=301s](https://www.youtube.com/watch?v=dB9JOsVx-yY&t=301s)

# Recyclerview Item Frame
- `MaterialCardView`  사용해서 stroke Color, Width 설정
- `layout_margin`을 줘서 아이템 간의 간격 유지
- `maxLines`을 통해서 textView 내용을 1줄로 제한한다
- `elevation` 아이템마다 그림자 효과를 줬다. ~~솔직히 잘 모르겠다~~
- `ellipsize`textview 내용이 1줄을 초과했을 때 효과를 준다.

end ← 마지막...

start ← 시작 ...

none ← 아예 끊기

> 출처
> 
> 
> [https://codeman77.tistory.com/54](https://codeman77.tistory.com/54)
