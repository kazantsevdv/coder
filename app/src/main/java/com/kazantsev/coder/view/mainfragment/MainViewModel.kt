package com.kazantsev.coder.view.mainfragment

import androidx.lifecycle.*
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.repo.UsersRepo
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val repo: UsersRepo,
) : ViewModel() {
    private var _items: MutableLiveData<List<DataItem>> = MutableLiveData()
    val item: LiveData<List<DataItem>> get() = _items

    private var _loadState = MutableLiveData<AppState<Any>>()
    var loadState: LiveData<AppState<Any>> = _loadState


    private var _department: MutableLiveData<List<Department>> = MutableLiveData()
    val department: LiveData<List<Department>> get() = _department

    private var _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String> get() = _query

    private var _sortByBirthday: MutableLiveData<Boolean> = MutableLiveData()
    val sortByBirthday: LiveData<Boolean> get() = _sortByBirthday

    init {
        loadFromApi()
    }

    fun getDepartment() {
        _department.value = repo.getDepartment()
    }

    fun loadFromApi() {
        viewModelScope.launch {
            try {
                if (repo.getUsers().isNullOrEmpty()) {
                    _loadState.value = AppState.Refresh()
                } else {
                    _loadState.value = AppState.Loading(null)
                }
                repo.getUsersFromApi()
                _loadState.value = AppState.Success(Any())

            } catch (exception: Exception) {

                if (!repo.getUsers().isNullOrEmpty()) {
                    _loadState.value = AppState.UpdateError(exception.localizedMessage ?: "")

                }else{
                    _loadState.value = AppState.Error(exception.localizedMessage ?: "")
                }

            }
        }
    }

    fun onNewQuery(query: String) {
        this._query.value = query
    }

    fun setBirthdaySort(sort: Boolean) {
        _sortByBirthday.value = sort
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

}