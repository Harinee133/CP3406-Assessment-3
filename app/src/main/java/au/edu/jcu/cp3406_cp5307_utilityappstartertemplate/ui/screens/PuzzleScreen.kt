package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.LearningTopic
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    viewModel: StatsViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val soundManager = viewModel.soundManager
    val puzzleData by viewModel.puzzleData.collectAsState()
    
    var flippedIndices by remember { mutableStateOf(setOf<Int>()) }
    var matchedIndices by remember { mutableStateOf(setOf<Int>()) }
    var score by remember { mutableIntStateOf(0) }
    var gameCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(activityId) {
        viewModel.loadPuzzleData(activityId)
    }

    if (puzzleData == null) return

    val pairs = puzzleData!!.pairs
    val gameItems = remember(puzzleData) {
        (pairs.map { it.first } + pairs.map { it.second }).shuffled()
    }

    LaunchedEffect(flippedIndices) {
        if (flippedIndices.size == 2) {
            val list = flippedIndices.toList()
            val item1 = gameItems[list[0]]
            val item2 = gameItems[list[1]]
            
            val isMatch = pairs.any { (it.first == item1 && it.second == item2) || (it.first == item2 && it.second == item1) }
            
            if (isMatch) {
                soundManager.playSuccess()
                matchedIndices = matchedIndices + flippedIndices
                score += 50
                flippedIndices = emptySet()
                if (matchedIndices.size == gameItems.size) gameCompleted = true
            } else {
                soundManager.playError()
                delay(1000)
                flippedIndices = emptySet()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Memory Mastery 🧠", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Score: $score", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(gameItems.size) { index ->
                    val isFlipped = flippedIndices.contains(index) || matchedIndices.contains(index)
                    MemoryCard(
                        content = gameItems[index],
                        isFlipped = isFlipped,
                        themeColor = Color(puzzleData!!.themeColor),
                        onClick = {
                            if (!isFlipped && flippedIndices.size < 2) {
                                flippedIndices = flippedIndices + index
                            }
                        }
                    )
                }
            }

            if (gameCompleted) {
                Button(onClick = {
                    viewModel.addStats(score)
                    onNavigateBack()
                }, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                    Text("Finish Quest! 🏆")
                }
            }
        }
    }
}

@Composable
fun MemoryCard(content: String, isFlipped: Boolean, themeColor: Color, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(400)
    )

    Card(
        modifier = Modifier.aspectRatio(1.2f).graphicsLayer { rotationY = rotation }.clickable(onClick = onClick),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(if (rotation > 90f) Color.White else themeColor),
            contentAlignment = Alignment.Center
        ) {
            if (rotation > 90f) {
                Text(content, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.graphicsLayer { rotationY = 180f })
            } else {
                Text("?", fontSize = 40.sp, color = Color.White)
            }
        }
    }
}
