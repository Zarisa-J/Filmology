package com.zarisa.filmology.main_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.network.Film
import com.zarisa.filmology.network.FilmApi
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class MainPageViewModel(app: Application) : AndroidViewModel(app) {
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus> = _status


    private val _films = MutableLiveData<List<Film>>()

    val films: LiveData<List<Film>> = _films

    init {
        getFilms()
    }

    private fun getFilms() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
            _films.value = FilmApi.retrofitService.getPopularMovies()
            _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _films.value = listOf()
            }
        }
    }
}