package com.kazantsev.coder.view.listfragment

import androidx.lifecycle.*
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.UsersRepo
import com.kazantsev.coder.view.mainfragment.DataItem
import com.kazantsev.coder.view.mainfragment.Department
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class ListViewModel @Inject constructor(
    private val repo: UsersRepo,
) : ViewModel() {
    private var _query: String = ""
    private var _items: MutableLiveData<List<DataItem>> = MutableLiveData()
    val item: LiveData<List<DataItem>> get() = _items

    private var _loadState = MutableLiveData<AppState<List<User>>>()
    var loadState: LiveData<AppState<List<User>>> = _loadState


    fun getUser(department: String) {
        val itemsList: MutableList<DataItem> = mutableListOf()
        viewModelScope.launch {
            try {
                val usersList = repo.getUsers()
                val filteredList: List<User>
                if (department.isNotBlank()) {
                    filteredList = usersList.filter { user -> user.department == department }
                } else {
                    filteredList = usersList
                }
                val queryList: List<User>
                if (_query.isNotEmpty()) {
                     queryList = filteredList.filter { user ->
                        user.name.contains(
                            _query,
                            true
                        ) || user.userTag.contains(_query, true)
                    }
                }else{
                    queryList=filteredList
                }


                for (user in queryList) {
                    itemsList.add(
                        DataItem.ItemName(
                            id = user.id,
                            avatarUrl = user.avatarUrl,
                            name = user.name,
                            userTag = user.userTag,
                            position = user.position
                        )
                    )
                }

                _items.value = itemsList


            } catch (exception: Exception) {

            }
        }
    }

    fun onNewQuery(query: String) {
        this._query = query
    }
//    private var _data = MutableLiveData<AppState<UserProfile>>()
//    var data: LiveData<AppState<UserProfile>> = _data
//
//    fun getUser(id: String) {
//        _data.value = AppState.Loading(null)
//        viewModelScope.launch {
//            try {
//                val user = repo.getUser(id)
//                val userProfile = UserProfile(
//                    id = id,
//                    avatarUrl = user.avatarUrl,
//                    name = user.name,
//                    userTag = user.userTag,
//                    department = user.department,
//                    position = user.position,
//                    birthday = getBirthday(user.birthday),
//                    years = getYears(user.birthday),
//                    phone = formatPhone(user.phone)
//                )
//                _data.value = AppState.Success(userProfile)
//            } catch (exception: Exception) {
//                _data.value = AppState.Error(exception.localizedMessage ?: "")
//            }
//        }
//    }

//    private fun getBirthday(date: Long): String {
//        val format = SimpleDateFormat(datePattern, Locale.getDefault())
//        return try {
//            format.format(date) ?: "---"
//        } catch (e: java.lang.Exception) {
//            "---"
//        }
//    }
//
//    private fun formatPhone(phone: String): String {
//        return PhoneNumberUtils.formatNumber("+7$phone", Locale.getDefault().country)
//    }
//
//    private fun getYears(data: Long): Int {
//        val format = DateTimeFormatter.ofPattern(datePattern)
//        val today: LocalDate = LocalDate.now()
//        val birthday: LocalDate = LocalDate.parse(getBirthday(data), format)
//        val p: Period = Period.between(birthday, today)
//        return p.years
//
//    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<ListViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == ListViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }

    companion object {
        const val datePattern = "d MMMM yyyy"
    }
}