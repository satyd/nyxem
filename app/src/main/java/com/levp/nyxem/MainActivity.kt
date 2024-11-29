package com.levp.nyxem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levp.nyxem.presentation.MainViewModel
import com.levp.nyxem.data.constants.Abilities
import com.levp.nyxem.presentation.UpdateIntent
import com.levp.nyxem.presentation.ValueUpdate
import com.levp.nyxem.ui.AbilityCounters
import com.levp.nyxem.ui.AbilityElement
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val dagonDrawableList = listOf<Int>(
                        R.drawable.ic_dagon_1,
                        R.drawable.ic_dagon_1,
                        R.drawable.ic_dagon_2,
                        R.drawable.ic_dagon_3,
                        R.drawable.ic_dagon_4,
                        R.drawable.ic_dagon_5,
                    )
                    val uiState = viewModel.uiState.collectAsState()
                    val abilityState = uiState.value.abilityState
                    val valueState = uiState.value.valueState

                    LaunchedEffect(
                        uiState.value.valueState,
                        uiState.value.abilityState
                    ) {
                        viewModel.validateInput()
                    }
                    //current total damage
                    val damage = viewModel.currentDamage.collectAsState()
                    val updateAbilityValue: (Boolean, Abilities) -> Unit = remember {
                        { increase, ability ->
                            viewModel.handleUpdate(UpdateIntent.UpdateAbility(increase, ability))
                            //viewModel.updateAbilityLevel(increase, ability)
                        }
                    }

                    var textDmg = ""
                    var textFieldValueState by remember {
                        mutableStateOf(
                            TextFieldValue(
                                text = valueState.attackDamage ?: "",
                                selection = TextRange(valueState.attackDamage?.length ?: 0)
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { focusManager.clearFocus() }) {

                        Spacer(modifier = Modifier.size(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AbilityElement(
                                drawableId = R.drawable.ic_impale,
                                name = "Impale",
                                currentLevel = abilityState.levelImpale,
                                ability = Abilities.Impale,
                                onClick = updateAbilityValue
                            )
                            AbilityElement(
                                drawableId = R.drawable.ic_mind_flare,
                                name = "Mind Flare",
                                currentLevel = abilityState.levelMindFlare,
                                ability = Abilities.MindFlare,
                                onClick = updateAbilityValue
                            )
                            AbilityElement(
                                drawableId = R.drawable.ic_vendetta,
                                name = "Vendetta",
                                currentLevel = abilityState.levelVendetta,
                                ability = Abilities.Vendetta,
                                onClick = updateAbilityValue
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val dagonLvl = abilityState.levelDagon.toIntOrNull() ?: 0
                            val phylacteryLvl = abilityState.levelPhylactery.toIntOrNull() ?: 0

                            AbilityElement(
                                drawableId = dagonDrawableList[dagonLvl],
                                name = "Dagon",
                                currentLevel = abilityState.levelDagon,
                                ability = Abilities.Dagon,
                                onClick = updateAbilityValue
                            )
                            AbilityElement(
                                drawableId = R.drawable.ic_ethereal,
                                name = "Ethereal",
                                currentLevel = abilityState.levelEthereal,
                                ability = Abilities.Ethereal,
                                onClick = updateAbilityValue
                            )
                            val (phylacteryDrawable, phylacteryName) =
                                if (phylacteryLvl <= 1) {
                                    R.drawable.ic_phylactery to "Phylactery"
                                } else {
                                    R.drawable.ic_khanda to "Khanda"
                                }
                            AbilityElement(
                                drawableId = phylacteryDrawable,
                                name = phylacteryName,
                                currentLevel = abilityState.levelPhylactery,
                                ability = Abilities.Phylactery,
                                onClick = updateAbilityValue
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        //target mana pool
                        //nyx attack damage (+number of hits)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ValueField(
                                name = "Attack Damage",
                                value = valueState.attackDamage,
                                modifier = Modifier,
                                onValueChange = { newValue ->
                                    viewModel.handleUpdate(
                                        UpdateIntent.UpdateValue(
                                            ValueUpdate.UpdateDamage(newValue.text)
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            AbilityCounters(
                                currentLevel = abilityState.numberOfAttacks,
                                ability = Abilities.Attack,
                                onClick = updateAbilityValue
                            )
                        }

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
                                    value = valueState.targetMaxMP,
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
                                    value = valueState.selfMagicAmplify,
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
                            Column {
                                //target magic resist
                                //target physical resist
                                ValueField(
                                    name = "Mag Res ",
                                    value = valueState.targetMagResist,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            UpdateIntent.UpdateValue(
                                                ValueUpdate.UpdateMagResistance(
                                                    newValue.text
                                                )
                                            )
                                        )
                                    }
                                )
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