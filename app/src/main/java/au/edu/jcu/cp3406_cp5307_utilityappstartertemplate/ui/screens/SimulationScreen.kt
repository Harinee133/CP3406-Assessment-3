package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulationScreen(
    viewModel: StatsViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val simData by viewModel.simulationData.collectAsState()
    var currentStepIdx by remember { mutableIntStateOf(0) }
    var currentVal by remember { mutableIntStateOf(0) }
    var userInput by remember { mutableStateOf("") }
    
    LaunchedEffect(activityId) {
        viewModel.loadSimulationData(activityId)
    }

    if (simData == null) return
    val step = simData!!.steps[currentStepIdx]

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(simData!!.title, fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text("Step ${currentStepIdx + 1} of ${simData!!.steps.size}", style = MaterialTheme.typography.titleMedium)
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                color = Color(simData!!.themeColor).copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(simData!!.themeColor))
            ) {
                Text(step.instruction, modifier = Modifier.padding(24.dp), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }

            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it.filter { c -> c.isDigit() } },
                label = { Text("Enter the result (${step.units})") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (userInput.toIntOrNull() == step.targetValue) {
                        viewModel.soundManager.playSuccess()
                        if (currentStepIdx < simData!!.steps.size - 1) {
                            currentStepIdx++
                            userInput = ""
                        } else {
                            viewModel.addStats(100)
                            onNavigateBack()
                        }
                    } else {
                        viewModel.soundManager.playError()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Confirm Result")
            }
        }
    }
}
