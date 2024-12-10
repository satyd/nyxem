package com.levp.nyxem.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.nyxem.data.AbilityState
import com.levp.nyxem.data.ValueState
import com.levp.nyxem.data.calculateDamage
import com.levp.nyxem.data.constants.Abilities
import com.levp.nyxem.data.toAbilityUiState
import com.levp.nyxem.presentation.uistates.ValueUiState
import com.levp.nyxem.presentation.uistates.toAbilityState
import com.levp.nyxem.presentation.uistates.toValueState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel : ViewModel() {

    private val mutableAbilityState = MutableStateFlow(AbilityState())
    val currentAbilityState = mutableAbilityState.asStateFlow()

    private val mutableValueState = MutableStateFlow(ValueState())
    val currentValueState = mutableValueState.asStateFlow()

    val uiState = MutableStateFlow(UiState.initState())

    val dataState = MutableStateFlow(UiState.initState())

    //Flow for deb
    private val inputFlow = MutableSharedFlow<UpdateIntent>()

    private val mutableDamage = MutableStateFlow("0.0")
    val currentDamage = mutableDamage.asStateFlow()

    init {
        viewModelScope.launch {
            inputFlow
                .debounce(2000L)
                .collectLatest { intent ->
                    //handleUpdate(intent)
                    //calculateDamage()
                }
        }
    }

    fun handleUpdate(update: UpdateIntent) {
        when (update) {
            UpdateIntent.Error -> TODO()
            is UpdateIntent.UpdateAbility -> {
                updateAbilityLevel(update.isIncrease, update.ability)
            }

            is UpdateIntent.UpdateValue -> {
                uiState.update { currState ->
                    currState.copy(
                        valueState = updateValue(update.valueUpdate)
                    )
                }
            }
        }
        //validateInput()
    }

    suspend fun validateInput() {
        uiState.update {
            it.copy(
                isLoading = true
            )
        }
        Log.i("hehe", "validate input ${uiState.value}")
        val values = uiState.value.valueState.toValueState()
        val abilities = uiState.value.abilityState.toAbilityState()
        var isError = false

        if ((values.targetMaxMP ?: 0) < 0 || (values.targetMaxMP ?: 0) > 6000) {
            isError = true
            uiState.update {
                it.copy(
                    isError = true
                )
            }
        }
        if (!isError) {
            uiState.update {
                it.copy(
                    isError = false
                )
            }
            dataState.update {
                it.copy(
                    abilityState = uiState.value.abilityState,
                    valueState = uiState.value.valueState
                )
            }
            calculateDamage()
        }

    }

    fun switchErrorState(isError: Boolean) {

    }

    fun updateAbilityLevel(isIncrease: Boolean, ability: Abilities) {
        viewModelScope.launch {
            val abilityLevel = when (ability) {
                Abilities.Impale -> currentAbilityState.value.levelImpale
                Abilities.MindFlare -> currentAbilityState.value.levelMindFlare
                Abilities.Vendetta -> currentAbilityState.value.levelVendetta
                Abilities.Dagon -> currentAbilityState.value.levelDagon
                Abilities.Attack -> currentAbilityState.value.numberOfAttacks
                Abilities.Ethereal -> currentAbilityState.value.levelEthereal
                Abilities.Phylactery -> currentAbilityState.value.levelPhylactery
            }
            val newValue = if (isIncrease) {
                if (abilityLevel < ability.maxLevel) {
                    abilityLevel + 1
                } else {
                    ability.maxLevel
                }
            } else {
                if (abilityLevel > 1) {
                    abilityLevel - 1
                } else {
                    0
                }
            }
            when (ability) {
                Abilities.Impale -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelImpale = newValue
                    )
                )

                Abilities.MindFlare -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelMindFlare = newValue
                    )
                )

                Abilities.Vendetta -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelVendetta = newValue
                    )
                )

                Abilities.Dagon -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelDagon = newValue
                    )
                )

                Abilities.Attack -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        numberOfAttacks = newValue
                    )
                )

                Abilities.Phylactery -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelPhylactery = newValue
                    )
                )
                Abilities.Ethereal -> mutableAbilityState.emit(
                    mutableAbilityState.value.copy(
                        levelEthereal = newValue
                    )
                )
            }
            uiState.update {
                it.copy(
                    abilityState = mutableAbilityState.value.toAbilityUiState()
                )
            }
        }
    }

    fun updateValue(newValue: ValueUpdate): ValueUiState {
        val curState = uiState.value.valueState
        val newState = when (newValue) {
            is ValueUpdate.UpdateDamage -> {
                curState.copy(
                    attackDamage = newValue.dmg
                )
            }

            is ValueUpdate.UpdateMaxMana -> {
                curState.copy(
                    targetMaxMP = newValue.maxMana
                )
                /*if (newValue.maxMana.isEmpty()) {
                    return oldValue.copy(targetMaxMP = null)
                }
                val intVal = newValue.maxMana.toIntOrNull() ?: -1
                if (intVal in 100..6000) {
                    oldValue.copy(targetMaxMP = newValue.maxMana)
                } else {
                    oldValue
                }*/
            }

            is ValueUpdate.UpdateMagResistance -> {
                curState.copy(
                    targetMagResist = newValue.magRes
                )
                /*if (newValue.magRes.isEmpty()) {
                    return oldValue.copy(targetMagResist = null)
                }
                val intVal = newValue.magRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValue.copy(targetMagResist = newValue.magRes)
                } else {
                    oldValue
                }*/
            }

            is ValueUpdate.UpdatePhysResistance -> {//hehe
                curState.copy(
                    targetPhysResist = newValue.physRes
                )
                /*val intVal = newValue.physRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValue.copy(targetPhysResist = newValue.physRes)
                } else {
                    oldValue
                }*/
            }

            is ValueUpdate.UpdateMagAmp -> {
                curState.copy(
                    spellAmp = newValue.magAmp
                )
            }
        }
        return newState
    }

    @SuppressLint("DefaultLocale")
    private suspend fun calculateDamage() {
        uiState
            .debounce(1000L)
            .collectLatest {
                val dmg = calculateDamage(
                    it.abilityState.toAbilityState(),
                    it.valueState.toValueState()
                )
                //val df = DecimalFormat("#.##")
                val formatted = String.format("%.2f", dmg).replace(",", ".")
                mutableDamage.emit(formatted)
                Log.i("hehe","damage updated")
                uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
    }
}