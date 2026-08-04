package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.Flashcard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.LearningTopic
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    viewModel: StatsViewModel,
    topic: LearningTopic,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val cards by viewModel.flashcards.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(topic, activityId) {
        viewModel.loadFlashcards(topic, activityId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Study Flashcards") },
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
            if (cards.isNotEmpty()) {
                val card = cards[currentIndex]
                
                Text(
                    "Card ${currentIndex + 1} of ${cards.size}",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / cards.size },
                    modifier = Modifier.fillMaxWidth().height(12.dp).clip(androidx.compose.foundation.shape.CircleShape),
                    color = Color(0xFF4FC3F7)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                FlashcardComponent(card)
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { if (currentIndex > 0) currentIndex-- },
                        enabled = currentIndex > 0,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                    ) {
                        Text("Previous")
                    }
                    
                    Button(
                        onClick = { 
                            if (currentIndex < cards.size - 1) {
                                currentIndex++
                            } else {
                                viewModel.addStats(100) // Finish bonus
                                onNavigateBack()
                            }
                        },
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                    ) {
                        Text(if (currentIndex < cards.size - 1) "Next" else "Finish")
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun FlashcardComponent(card: Flashcard) {
    var rotated by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { rotated = !rotated },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (rotation <= 90f || rotation >= 270f) Color(0xFFE1F5FE) else Color(0xFFFFF9C4)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                Text(
                    text = card.front,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0278AE)
                )
            } else {
                Text(
                    text = card.back,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF57C00),
                    modifier = Modifier.graphicsLayer { rotationY = 180f }
                )
            }
        }
    }
}