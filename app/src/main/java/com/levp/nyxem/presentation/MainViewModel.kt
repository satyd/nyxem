package com.levp.nyxem.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.nyxem.data.AbilityState
import com.levp.nyxem.data.ValueState
import com.levp.nyxem.domain.calculateDamage
import com.levp.nyxem.domain.constants.Abilities
import com.levp.nyxem.domain.constants.Properties
import com.levp.nyxem.data.toAbilityUiState
import com.levp.nyxem.domain.AllowedValues
import com.levp.nyxem.domain.ValueError
import com.levp.nyxem.presentation.uistates.ValueUiState
import com.levp.nyxem.presentation.uistates.toAbilityState
import com.levp.nyxem.presentation.uistates.toValueState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel : ViewModel() {

    // Data States
    private val mutableAbilityState = MutableStateFlow(AbilityState())
    val currentAbilityState = mutableAbilityState.asStateFlow()

    private val mutableValueState = MutableStateFlow(ValueState())
    val currentValueState = mutableValueState.asStateFlow()

    // Ui State
    val uiState = MutableStateFlow(UiState.initState())

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
            UpdateIntent.Error -> {
                TODO()
            }

            is UpdateIntent.UpdateAbility -> {
                updateAbilityLevel(update.isIncrease, update.ability)
            }

            is UpdateIntent.UpdateCounter -> {
                val updateValue = when (update.property) {
                    Properties.MagicRes -> {
                        ValueUpdate.UpdateMagResistance(
                            (currentValueState.value.targetMagResist.toInt() + 1).toString()
                        )
                    }

                    Properties.PhysicalRes -> TODO()
                }
                //updateValue(update.isIncrease, update.property)
            }

            is UpdateIntent.UpdateValue -> {
                uiState.update { state ->
                    state.copy(
                        valueState = updateValueState(update.valueUpdate)
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

        if ((values.targetMaxMana ?: 0) < 0 || (values.targetMaxMana ?: 0) > 6000) {
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
            /*dataState.update {
                it.copy(
                    abilityState = uiState.value.abilityState,
                    valueState = uiState.value.valueState
                )
            }*/
            calculateDamage()
        }

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

    fun updateValueState(newValue: ValueUpdate): ValueUiState {
        val currentState = uiState.value.valueState
        var isError = true
        lateinit var valueError: ValueError
        val intVal = newValue.value.toIntOrNull() ?: -1

        val newState = when (newValue) {
            is ValueUpdate.UpdateDamage -> {
                valueError = ValueError.WrongAttackDamage
                if (AllowedValues.AttackDamage.range.isInRange(intVal)) {
                    updateDataValue(newValue)
                    isError = false
                }

                currentState.copy(
                    attackDamage = newValue.value
                )
            }

            is ValueUpdate.UpdateMaxMana -> {
                valueError = ValueError.WrongMaxMana
                if (AllowedValues.MaxMana.range.isInRange(intVal)) {
                    updateDataValue(newValue)
                    isError = false
                }

                currentState.copy(
                    targetMaxMana = newValue.value
                )
            }

            is ValueUpdate.UpdateMagResistance -> {
                valueError = ValueError.WrongMagicResistance
                if (AllowedValues.MagicResistance.range.isInRange(intVal)) {
                    updateDataValue(newValue)
                    isError = false
                }

                currentState.copy(
                    targetMagResist = newValue.value
                )
            }

            is ValueUpdate.UpdatePhysResistance -> {//hehe
                valueError = ValueError.WrongPhysicalResistance
                if (AllowedValues.PhysicalResistance.range.isInRange(intVal)) {
                    updateDataValue(newValue)
                    isError = false
                }

                currentState.copy(
                    targetPhysResist = newValue.value
                )
            }

            is ValueUpdate.UpdateMagAmp -> {
                valueError = ValueError.WrongMagicAmplify
                if (AllowedValues.MagicAmplify.range.isInRange(intVal)) {
                    updateDataValue(newValue)
                    isError = false
                }
                currentState.copy(
                    spellAmp = newValue.value
                )
            }
        }
        updateErrors(valueError, isError)
        return newState
    }

    private fun updateErrors(error: ValueError, isAdd: Boolean) {
        uiState.update { state ->
            if (isAdd) {
                state.copy(
                    isError = true,
                    listOfErrors = state.listOfErrors + error
                )
            } else {
                val newErrorList = state.listOfErrors.filter { it != error }
                state.copy(
                    isError = newErrorList.isNotEmpty(),
                    listOfErrors = newErrorList
                )
            }
        }
    }

    private fun updateDataValue(newValue: ValueUpdate) {
        viewModelScope.launch {
            mutableValueState.update { valueState ->
                val value = newValue.value.toInt()
                when (newValue) {
                    is ValueUpdate.UpdateDamage -> {
                        valueState.copy(
                            attackDamage = value
                        )
                    }

                    is ValueUpdate.UpdateMagAmp -> {
                        valueState.copy(
                            spellAmp = value
                        )
                    }

                    is ValueUpdate.UpdateMagResistance -> {
                        valueState.copy(
                            targetMagResist = value
                        )
                    }

                    is ValueUpdate.UpdateMaxMana -> {
                        valueState.copy(
                            targetMaxMana = value
                        )
                    }

                    is ValueUpdate.UpdatePhysResistance -> {
                        valueState.copy(
                            targetPhysResist = value
                        )
                    }
                }
            }
            calculateDamage()
        }
    }

    @SuppressLint("DefaultLocale")
    private suspend fun calculateDamage() {
        Log.v("hehe","calc call ")
        currentAbilityState
            .debounce(1000L)
            .combine(currentValueState.debounce(1000L)) { abilityState, valueState ->
                calculateDamage(
                    abilityState,
                    valueState
                )
            }
            .debounce(1000L)
            .collectLatest { dmg ->
                //val df = DecimalFormat("#.##")
                val formatted = String.format("%.2f", dmg).replace(",", ".")
                mutableDamage.emit(formatted)
                Log.i("hehe", "damage updated $dmg")
                uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
    }
}