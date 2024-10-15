package com.levp.nyxem.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.nyxem.data.AbilitiesState
import com.levp.nyxem.data.calculateDamage
import com.levp.nyxem.data.constants.Abilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val mutableAbilityState = MutableStateFlow(AbilitiesState())
    val currentAbilityState = mutableAbilityState.asStateFlow()

    val valuesState = MutableStateFlow(ValuesState.initialState())

    val mutableDamage = MutableStateFlow("0.0")
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
        valuesState.value = reduce(valuesState.value, update)
        viewModelScope.launch {
            calculateDamage()
        }
    }

    fun reduce(oldValues: ValuesState, update: ValueUpdate): ValuesState {
        return when (update) {
            is ValueUpdate.UpdateDamage -> {
                oldValues.copy(attackDamage = update.dmg)
            }

            is ValueUpdate.UpdateMaxMana -> {
                val intVal = update.maxMana.toIntOrNull() ?: -1
                if (intVal in 100..6000) {
                    oldValues.copy(targetMaxMP = update.maxMana)
                } else {
                    oldValues
                }
            }

            is ValueUpdate.UpdateMagResistance -> {
                val intVal = update.magRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValues.copy(targetMagResist = update.magRes)
                } else {
                    oldValues
                }
            }

            is ValueUpdate.UpdatePhysResistance -> {
                val intVal = update.physRes.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValues.copy(targetPhysResist = update.physRes)
                } else {
                    oldValues
                }
            }

            is ValueUpdate.UpdateMagAmp -> {
                val intVal = update.magAmp.toIntOrNull() ?: -1
                if (intVal in 0..100) {
                    oldValues.copy(selfMagicAmplify = update.magAmp)
                } else {
                    oldValues
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
        val dmg = calculateDamage(currentAbilityState.value, valuesState.value)
        //val df = DecimalFormat("#.##")
        val formatted = String.format("%.2f", dmg).replace(",", ".")
        mutableDamage.emit(formatted)
    }
}