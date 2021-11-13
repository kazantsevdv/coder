package com.kazantsev.coder.view.mainfragment

import androidx.lifecycle.*
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.UsersRepo
import com.kazantsev.coder.view.mainfragment.DataItem
import com.kazantsev.coder.view.mainfragment.Department
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val repo: UsersRepo,
) : ViewModel() {
    private var _items: MutableLiveData<List<DataItem>> = MutableLiveData()
    val item: LiveData<List<DataItem>> get() = _items

    private var _loadState = MutableLiveData<AppState<List<User>>>()
    var loadState: LiveData<AppState<List<User>>> = _loadState


    private var _department: MutableLiveData<List<Department>> = MutableLiveData()
    val department: LiveData<List<Department>> get() = _department

    init {
        loadFromApi()
    }

    fun getDepartment() {
        _department.value = repo.getDepartment()
    }

    fun loadFromApi() {
        viewModelScope.launch {
            try {
                _loadState.value = AppState.Loading(null)
                repo.getUsersFromApi()
                _loadState.value = AppState.Success(listOf())
            } catch (exception: Exception) {
                _loadState.value = AppState.Error(exception.localizedMessage ?: "")
            }
        }
    }




    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<MainViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }

    companion object {
        const val datePattern = "d MMMM yyyy"
    }
}