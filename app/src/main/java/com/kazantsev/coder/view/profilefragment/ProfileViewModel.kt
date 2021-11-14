package com.kazantsev.coder.view.profilefragment

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.*
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.repo.UsersRepo
import com.kazantsev.coder.util.toYearsString
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileViewModel @Inject constructor(
    private val repo: UsersRepo,
) : ViewModel() {
    private var _data = MutableLiveData<AppState<UserProfile>>()
    var data: LiveData<AppState<UserProfile>> = _data

    fun getUser(id: String) {
        _data.value = AppState.Loading(null)
        viewModelScope.launch {
            try {
                val user = repo.getUser(id)
                val userProfile = UserProfile(
                    id = id,
                    avatarUrl = user.avatarUrl,
                    name = user.name,
                    userTag = user.userTag,
                    department = user.department,
                    position = user.position,
                    birthday = getBirthday(user.birthday),
                    years = getYears(user.birthday).toYearsString(),
                    phone = formatPhone(user.phone)
                )
                _data.value = AppState.Success(userProfile)
            } catch (exception: Exception) {
                _data.value = AppState.Error(exception.localizedMessage ?: "")
            }
        }
    }

    private fun getBirthday(date: Long): String {
        val format = SimpleDateFormat(datePattern, Locale.getDefault())
        return try {
            format.format(date) ?: "---"
        } catch (e: java.lang.Exception) {
            "---"
        }
    }

    private fun formatPhone(phone: String): String {
        return PhoneNumberUtils.formatNumber("+7$phone", Locale.getDefault().country)
    }

    private fun getYears(data: Long): Int {
        val format = DateTimeFormatter.ofPattern(datePattern)
        val today: LocalDate = LocalDate.now()
        val birthday: LocalDate = LocalDate.parse(getBirthday(data), format)
        val p: Period = Period.between(birthday, today)
        return p.years

    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<ProfileViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == ProfileViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }

    companion object {
        const val datePattern = "d MMMM yyyy"
    }
}