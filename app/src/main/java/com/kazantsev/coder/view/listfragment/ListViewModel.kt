package com.kazantsev.coder.view.listfragment

import android.util.Log
import androidx.lifecycle.*
import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.UsersRepo
import com.kazantsev.coder.view.mainfragment.DataItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ListViewModel @Inject constructor(
    private val repo: UsersRepo,
) : ViewModel() {
    private var _query: String = ""
    private var _sortByBirthday = false
    private var _items: MutableLiveData<List<DataItem>> = MutableLiveData()
    val item: LiveData<List<DataItem>> get() = _items

    fun getUser(department: String) {
        val itemsList: MutableList<DataItem> = mutableListOf()
        viewModelScope.launch {
            try {
                val usersList = repo.getUsers()

                val filteredList: List<User> = if (department.isNotBlank()) {
                    usersList.filter { user -> user.department == department }
                } else {
                    usersList
                }

                val queryList: List<User> = if (_query.isNotEmpty()) {
                    filteredList.filter { user ->
                        user.name.contains(
                            _query,
                            true
                        ) || user.userTag.contains(_query, true)
                    }
                } else {
                    filteredList
                }

                val sortedList: List<User> = if (_sortByBirthday) {
                    queryList.sortedWith(compareBy { dayBeforeBirthday(it.birthday) })
                } else {
                    queryList
                }
                if (!_sortByBirthday) {
                    for (user in sortedList) {
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
                } else {
                    var isDelimiterInserted = false
                    for (user in sortedList) {
                        if (!isDelimiterInserted && birthdayIsNextYear(user.birthday)) {
                            itemsList.add(DataItem.Delimiter(nextYear().toString()))
                            isDelimiterInserted = true
                        }
                        itemsList.add(
                            DataItem.ItemBirthday(
                                id = user.id,
                                avatarUrl = user.avatarUrl,
                                name = user.name,
                                userTag = user.userTag,
                                position = user.position,
                                birthday = getBirthday(user.birthday, datePatternView)
                            )
                        )
                    }

                }
                _items.value = itemsList


            } catch (exception: Exception) {
                Log.d("111", exception.localizedMessage ?: "")
            }
        }
    }


    fun onNewQuery(query: String) {
        this._query = query
    }

    fun setBirthdaySort(sort: Boolean) {
        _sortByBirthday = sort
    }

    private fun dayBeforeBirthday(data: Long): Int {

        val format = DateTimeFormatter.ofPattern(datePattern)

        val today: LocalDate = LocalDate.now()
        val tBirthDay: LocalDate =
            LocalDate.parse(getBirthday(data, datePattern), format).withYear(today.year)

        var dayBeforeBirthday = tBirthDay.dayOfYear - today.dayOfYear
        if (dayBeforeBirthday < 0) {
            dayBeforeBirthday += LocalDate.of(today.year + 1, 12, 31).dayOfYear
        }
        return dayBeforeBirthday
    }

    private fun nextYear() = LocalDate.now().year + 1


    private fun getBirthday(date: Long, pattern: String): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            format.format(date) ?: "---"
        } catch (e: java.lang.Exception) {
            "---"
        }
    }

    private fun birthdayIsNextYear(birthday: Long): Boolean {
        val dayBeforeBirthday = dayBeforeBirthday(birthday)
        val today: LocalDate = LocalDate.now()
        val daysInYear = LocalDate.of(today.year, 12, 31).dayOfYear
        return (daysInYear - today.dayOfYear) < dayBeforeBirthday
    }

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
        const val datePattern = "d MMM yyyy"
        const val datePatternView = "d MMM"


    }
}