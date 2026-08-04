package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    viewModel: StatsViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val questions by viewModel.questions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResults by remember { mutableStateOf(false) }

    LaunchedEffect(activityId) {
        viewModel.fetchQuestions(activityId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Math Quest") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (showResults) {
                ResultContent(score = score, total = questions.size, onNavigateBack = onNavigateBack)
            } else if (questions.isNotEmpty()) {
                val question = questions[currentQuestionIndex]
                val allAnswers = remember(question) {
                    (question.incorrectAnswers + question.correctAnswer).shuffled()
                }

                QuestionContent(
                    questionText = question.question,
                    answers = allAnswers,
                    currentIndex = currentQuestionIndex,
                    totalQuestions = questions.size,
                    onAnswerSelected = { answer ->
                        if (answer == question.correctAnswer) {
                            viewModel.soundManager.playSuccess()
                            score += 20
                        } else {
                            viewModel.soundManager.playError()
                        }
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            viewModel.addStats(score)
                            showResults = true
                        }
                    }
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No questions available.", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.fetchQuestions(activityId) }) {
                        Text("Try Again")
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionContent(
    questionText: String,
    answers: List<String>,
    currentIndex: Int,
    totalQuestions: Int,
    onAnswerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Quest Progress: ${currentIndex + 1}/$totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalQuestions },
                modifier = Modifier.fillMaxWidth().height(16.dp).clip(androidx.compose.foundation.shape.CircleShape),
                color = Color(0xFF81C784),
                trackColor = Color(0xFFE0E0E0),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        val decodedQuestion = Html.fromHtml(questionText, Html.FROM_HTML_MODE_LEGACY).toString()
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(40.dp),
            color = Color(0xFFFFF9C4),
            shadowElevation = 8.dp
        ) {
            Box(modifier = Modifier.padding(32.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = decodedQuestion,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF5D4037),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        answers.forEachIndexed { index, answer ->
            val decodedAnswer = Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY).toString()
            val buttonColor = when(index % 3) {
                0 -> Color(0xFF4FC3F7)
                1 -> Color(0xFFF06292)
                else -> Color(0xFFFFB74D)
            }
            
            Button(
                onClick = { onAnswerSelected(answer) },
                modifier = Modifier.fillMaxWidth().height(68.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(decodedAnswer, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ResultContent(score: Int, total: Int, onNavigateBack: () -> Unit) {
    Surface(
        modifier = Modifier.padding(24.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(40.dp),
        color = Color(0xFFE1F5FE),
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("🎉 AMAZING! 🎉", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0288D1))
            
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(160.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "🏆",
                            style = MaterialTheme.typography.displayLarge,
                            fontSize = 80.sp
                        )
                    }
                }
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).offset(x = (-10).dp, y = (-10).dp).size(60.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFFFFD54F),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("${(score.toFloat() / (total * 20) * 100).toInt()}%", fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Text(
                text = if (score >= 80) "You are a Math Rockstar! 🎸" 
                       else if (score >= 50) "Super Effort! 🚀" 
                       else "Great Try! Keep Going! 🎈",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            
            Text("Score: $score", style = MaterialTheme.typography.titleLarge)
            
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DB6AC))
            ) {
                Text("Go to Hub 🏠", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}