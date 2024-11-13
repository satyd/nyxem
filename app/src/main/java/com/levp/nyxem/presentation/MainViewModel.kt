package com.levp.nyxem.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.nyxem.data.AbilityState
import com.levp.nyxem.data.ValueState
import com.levp.nyxem.data.calculateDamage
import com.levp.nyxem.data.constants.Abilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mutableAbilityState = MutableStateFlow(AbilityState())
    val currentAbilityState = mutableAbilityState.asStateFlow()

    val valueState = MutableStateFlow(ValueState.initialState())

    private val mutableDamage = MutableStateFlow("0.0")
    val currentDamage = mutableDamage.asStateFlow()


    fun updateAbilityLevel(isIncrease: Boolean, ability: Abilities) {
        viewModelScope.launch {
            val abilityLevel = when (ability) {
                Abilities.Impale -> currentAbilityState.value.levelImpale
                Abilities.MindFlare -> currentAbilityState.value.levelMindFlare
                Abilities.Vendetta -> currentAbilityState.value.levelVendetta
                Abilities.Dagon -> currentAbilityState.value.levelDagon
                Abilities.Attack -> currentAbilityState.value.numberOfAttacks
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
            }
            calculateDamage()
        }
    }

    fun handleUpdate(update: ValueUpdate) {
        valueState.value = reduce(valueState.value, update)
        viewModelScope.launch {
            calculateDamage()
        }
    }

    fun reduce(oldValue: ValueState, newValue: ValueUpdate): ValueState {
        return when (newValue) {
            is ValueUpdate.UpdateDamage -> {
                if(newValue.dmg.isNotEmpty()) {
                    oldValue.copy(attackDamage = newValue.dmg)
                } else {
                    oldValue.copy(attackDamage = null)
                }
            }

            is ValueUpdate.UpdateMaxMana -> {
                if (newValue.maxMana.isEmpty()) {
                    return oldValue.copy(targetMaxMP = null)
                }
                val intVal = newValue.maxMana.toIntOrNull() ?: -1
                if (intVal in 100..6000) {
                    oldValue.copy(targetMaxMP = newValue.maxMana)
                } else {
                    oldValue
                }
            }

            is ValueUpdate.UpdateMagResistance -> {
                if (newValue.magRes.isEmpty()) {
                    return oldValue.copy(targetMagResist = null)
                }
                val intVal = newValue.magRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValue.copy(targetMagResist = newValue.magRes)
                } else {
                    oldValue
                }
            }

            is ValueUpdate.UpdatePhysResistance -> {//hehe
                val intVal = newValue.physRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValue.copy(targetPhysResist = newValue.physRes)
                } else {
                    oldValue
                }
            }

            is ValueUpdate.UpdateMagAmp -> {
                val intVal = newValue.magAmp.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValue.copy(selfMagicAmplify = newValue.magAmp)
                } else {
                    oldValue
                }
            }
        }

    }

    /*fun changeDamage(newDamage: String) {
        //currentMessage.tryEmit(message)
        viewModelScope.launch {
            if (newDamage.isNotEmpty()) {
                if (newDamage.isDigitsOnly()) {
                    val intDmg = newDamage.toInt()
                    if (intDmg < 3000) {
                        mutableAbilityState.emit(currentAbilityState.value.copy(attackDamage = intDmg.toString()))
                        calculateDamage()
                    }
                } // hehe else incorrect dmg value
            } else {
                mutableAbilityState.emit(currentAbilityState.value.copy(attackDamage = ""))
            }
        }
    }*/

    private suspend fun calculateDamage() {
        val dmg = calculateDamage(currentAbilityState.value, valueState.value)
        //val df = DecimalFormat("#.##")
        val formatted = String.format("%.2f", dmg).replace(",", ".")
        mutableDamage.emit(formatted)
    }
}