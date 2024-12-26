package com.levp.nyxem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levp.nyxem.presentation.MainViewModel
import com.levp.nyxem.domain.constants.Abilities
import com.levp.nyxem.presentation.UpdateIntent
import com.levp.nyxem.presentation.ValueUpdate
import com.levp.nyxem.presentation.item_rows.AbilityRow
import com.levp.nyxem.presentation.item_rows.AutoAttackRow
import com.levp.nyxem.presentation.item_rows.ItemRow
import com.levp.nyxem.presentation.item_rows.TargetMagResRow
import com.levp.nyxem.ui.ResultPart
import com.levp.nyxem.ui.ValueField
import com.levp.nyxem.ui.theme.NyxemTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NyxemTheme {
                val focusManager = LocalFocusManager.current

                // A surface container using the 'background' color from the theme
                Scaffold (
                    modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
                ) { paddingValues->

                    val uiState = viewModel.uiState.collectAsState()
                    val abilityState = uiState.value.abilityState
                    val valueState = uiState.value.valueState

                    LaunchedEffect(
                        uiState.value.valueState,
                        uiState.value.abilityState
                    ) {
                        //viewModel.validateInput()
                    }
                    //current total damage
                    val damage = viewModel.currentDamage.collectAsState()
                    val updateAbilityValue: (Boolean, Abilities) -> Unit = remember {
                        { increase, ability ->
                            viewModel.handleUpdate(UpdateIntent.UpdateAbility(increase, ability))
                        }
                    }

                    /*val updateCounterValue: (Boolean, Properties) -> Unit = remember {
                        { increase, property ->
                            viewModel.handleUpdate(UpdateIntent.UpdateAbility(increase, property))
                        }
                    }*/

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .clickable { focusManager.clearFocus() }) {

                        Spacer(modifier = Modifier.size(12.dp))
                        AbilityRow(
                            abilityState = abilityState,
                            updateAbilityValue = updateAbilityValue,
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        ItemRow(
                            abilityState = abilityState,
                            updateAbilityValue = updateAbilityValue,
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        //target mana pool
                        //nyx attack damage (+number of hits)
                        AutoAttackRow(
                            abilityState = abilityState,
                            valueState = valueState,
                            updateAbilityValue = updateAbilityValue,
                            onValueChange = { newValue ->
                                viewModel.handleUpdate(
                                    UpdateIntent.UpdateValue(
                                        ValueUpdate.UpdateDamage(newValue.text)
                                    )
                                )
                            }
                        )

                        Spacer(modifier = Modifier.size(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                        ) {
                            Column {
                                ValueField(
                                    name = "Max Mana",
                                    value = valueState.targetMaxMana,
                                    isPercent = false,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            UpdateIntent.UpdateValue(
                                                ValueUpdate.UpdateMaxMana(
                                                    newValue.text
                                                )
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                ValueField(
                                    name = "Mag Amp ",
                                    value = valueState.spellAmp,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            UpdateIntent.UpdateValue(
                                                ValueUpdate.UpdateMagAmp(
                                                    newValue.text
                                                )
                                            )
                                        )
                                    }
                                )
                            }
                            TargetMagResRow(valueState = valueState)  { newValue ->
                                viewModel.handleUpdate(
                                    UpdateIntent.UpdateValue(
                                        ValueUpdate.UpdateMagResistance(
                                            newValue.text
                                        )
                                    )
                                )
                            }
                            Column {
                                //target magic resist
                                //target physical resist

                                Spacer(modifier = Modifier.size(12.dp))
                                ValueField(
                                    name = "Phys Res",
                                    value = valueState.targetPhysResist,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            UpdateIntent.UpdateValue(
                                                ValueUpdate.UpdatePhysResistance(
                                                    newValue.text
                                                )
                                            )
                                        )
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        ResultPart(
                            isError = uiState.value.isError,
                            isLoading = uiState.value.isLoading,
                            totalDamageStr = damage.value
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NyxemTheme {

    }
}