package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.LearningTopic
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordQuestScreen(
    viewModel: StatsViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    // Content mapping based on activity ID
    val words = when(activityId) {
        "e1" -> listOf("APPLE", "BREAD", "CHAIR", "DREAM", "EARTH") // P1 foundational
        "e4" -> listOf("DISCERN", "CONTEXT", "CRITICAL", "LITERACY") // P4 STELLAR
        "e8" -> listOf("REPORT", "NOTICE", "MESSAGE", "URGENT") // P6 Functional
        else -> listOf("CREATIVE", "INQUIRY", "COLLAB", "ADAPT", "CRITICAL")
    }
    var currentWordIndex by remember { mutableIntStateOf(0) }
    val currentWord = words[currentWordIndex]
    val scrambledWord = remember(currentWord) { currentWord.toList().shuffled().joinToString("") }
    
    var userInput by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("STELLAR Word Quest 📚", fontWeight = FontWeight.Black) },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Unscramble the 21st Century Competency!", 
                style = MaterialTheme.typography.titleMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Surface(
                color = Color(0xFFE1F5FE),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = scrambledWord,
                    modifier = Modifier.padding(32.dp),
                    style = MaterialTheme.typography.displayMedium,
                    letterSpacing = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0277BD)
                )
            }

            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it.uppercase() },
                label = { Text("Enter unscrambled word") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (userInput == currentWord) {
                        viewModel.soundManager.playSuccess()
                        feedback = "Correct! 🌟"
                        score += 20
                        if (currentWordIndex < words.size - 1) {
                            currentWordIndex++
                            userInput = ""
                        } else {
                            viewModel.addStats(score, LearningTopic.ENGLISH)
                            feedback = "Quest Complete! 🎉"
                        }
                    } else {
                        viewModel.soundManager.playError()
                        feedback = "Try again! ❌"
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Check Word")
            }

            Text(feedback, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            
            if (feedback == "Quest Complete! 🎉") {
                Button(onClick = {
                    viewModel.addStats(score, LearningTopic.ENGLISH)
                    onNavigateBack()
                }) {
                    Text("Return to Hub")
                }
            }
        }
    }
}
