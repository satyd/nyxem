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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.levp.nyxem.presentation.ValueUpdate
import com.levp.nyxem.ui.AbilityCounters
import com.levp.nyxem.ui.AbilityElement
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

                    val abilityState = viewModel.currentAbilityState.collectAsState()
                    val valuesState = viewModel.valueState.collectAsState()

                    val damage = viewModel.currentDamage.collectAsState()
                    val updateAbilityValue: (Boolean, Abilities) -> Unit = remember {
                        { increase, ability ->
                            viewModel.updateAbilityLevel(increase, ability)
                        }
                    }

                    var textDmg = valuesState.value.attackDamage
                    var textFieldValueState by remember {
                        mutableStateOf(
                            TextFieldValue(
                                text = valuesState.value.attackDamage ?: "",
                                selection = TextRange(valuesState.value.attackDamage?.length ?: 0)
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
                                currentLevel = abilityState.value.levelImpale.toString(),
                                ability = Abilities.Impale,
                                onClick = updateAbilityValue
                            )
                            AbilityElement(
                                drawableId = R.drawable.ic_mind_flare,
                                name = "Mind Flare",
                                currentLevel = abilityState.value.levelMindFlare.toString(),
                                ability = Abilities.MindFlare,
                                onClick = updateAbilityValue
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AbilityElement(
                                drawableId = R.drawable.ic_vendetta,
                                name = "Vendetta",
                                currentLevel = abilityState.value.levelVendetta.toString(),
                                ability = Abilities.Vendetta,
                                onClick = updateAbilityValue
                            )
                            AbilityElement(
                                drawableId = R.drawable.ic_dagon_1,
                                name = "Dagon",
                                currentLevel = abilityState.value.levelDagon.toString(),
                                ability = Abilities.Dagon,
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
                                value = valuesState.value.attackDamage,
                                modifier = Modifier,
                                onValueChange = { newValue ->
                                    viewModel.handleUpdate(ValueUpdate.UpdateDamage(newValue.text))
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            AbilityCounters(
                                currentLevel = abilityState.value.numberOfAttacks.toString(),
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
                                    value = valuesState.value.targetMaxMP,
                                    isPercent = false,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            ValueUpdate.UpdateMaxMana(
                                                newValue.text
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                ValueField(
                                    name = "Mag Amp ",
                                    value = valuesState.value.selfMagicAmplify,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            ValueUpdate.UpdateMagAmp(
                                                newValue.text
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
                                    value = valuesState.value.targetMagResist,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            ValueUpdate.UpdateMagResistance(
                                                newValue.text
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                ValueField(
                                    name = "Phys Res",
                                    value = valuesState.value.targetPhysResist,
                                    isPercent = true,
                                    modifier = Modifier,
                                    onValueChange = { newValue ->
                                        viewModel.handleUpdate(
                                            ValueUpdate.UpdatePhysResistance(
                                                newValue.text
                                            )
                                        )
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Total damage: ${damage.value}")
                        }
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