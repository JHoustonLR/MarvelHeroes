package com.example.marvel.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.HeroesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeroViewModel @Inject constructor(
    private val heroRepository: HeroesRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<HeroState>(HeroState.Loading)
    val stateFlow: StateFlow<HeroState> = _stateFlow

    fun loadHero(heroId: String) {
        if (_stateFlow.value is HeroState.Success) return

        viewModelScope.launch {
            _stateFlow.value = HeroState.Loading
            try {
                val superhero = heroRepository.getHeroById(heroId)
                _stateFlow.value = HeroState.Success(superhero)
            } catch (e: Exception) {
                Log.e("HeroViewModel", "Не удалось загрузить героя", e)
                _stateFlow.value = HeroState.Error
            }
        }
    }
}
