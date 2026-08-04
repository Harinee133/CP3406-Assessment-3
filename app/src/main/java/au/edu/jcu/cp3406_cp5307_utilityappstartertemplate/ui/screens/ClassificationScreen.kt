package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.LearningTopic
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel

data class ClassItem(val name: String, val isLiving: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationScreen(
    viewModel: StatsViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val items = remember(activityId) {
        listOf(
            ClassItem("🐘 Elephant", true),
            ClassItem("🪨 Rock", false),
            ClassItem("🌻 Sunflower", true),
            ClassItem("🚗 Car", false),
            ClassItem("🍄 Mushroom", true),
            ClassItem("📱 Phone", false)
        ).shuffled()
    }
    
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResults by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Science Classification 🧪", fontWeight = FontWeight.Black) },
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
            verticalArrangement = Arrangement.Center
        ) {
            if (!showResults) {
                val currentItem = items[currentIndex]
                
                Text("Is this a Living Thing?", style = MaterialTheme.typography.headlineSmall)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Surface(
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    modifier = Modifier.size(200.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(currentItem.name, style = MaterialTheme.typography.displaySmall)
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = {
                            if (currentItem.isLiving) {
                                viewModel.soundManager.playSuccess()
                                score += 20
                            } else {
                                viewModel.soundManager.playError()
                            }
                            if (currentIndex < items.size - 1) currentIndex++ else showResults = true
                        },
                        modifier = Modifier.weight(1f).height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                    ) {
                        Text("YES")
                    }
                    
                    Button(
                        onClick = {
                            if (!currentItem.isLiving) {
                                viewModel.soundManager.playSuccess()
                                score += 20
                            } else {
                                viewModel.soundManager.playError()
                            }
                            if (currentIndex < items.size - 1) currentIndex++ else showResults = true
                        },
                        modifier = Modifier.weight(1f).height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
                    ) {
                        Text("NO")
                    }
                }
            } else {
                Text("Science Quest Complete!", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("Score: $score", style = MaterialTheme.typography.headlineMedium)
                
                Button(onClick = {
                    viewModel.addStats(score, LearningTopic.SCIENCE)
                    onNavigateBack()
                }, modifier = Modifier.padding(top = 32.dp)) {
                    Text("Return to Hub")
                }
            }
        }
    }
}
