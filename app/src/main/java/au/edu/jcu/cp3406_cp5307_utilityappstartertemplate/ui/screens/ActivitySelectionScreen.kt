package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.*
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySelectionScreen(
    viewModel: StatsViewModel,
    onNavigateToQuiz: (String) -> Unit,
    onNavigateToFlashcards: (LearningTopic, String) -> Unit,
    onNavigateToPuzzle: (String) -> Unit,
    onNavigateToSimulation: (String) -> Unit,
    onNavigateToWordQuest: (String) -> Unit,
    onNavigateToClassification: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val activities by viewModel.activities.collectAsState()
    var selectedTopic by remember { mutableStateOf<LearningTopic?>(null) }
    var selectedLevel by remember { mutableStateOf<PrimaryLevel?>(null) }
    var selectedSyllabus by remember { mutableStateOf<SyllabusType?>(null) }

    // Bottom Sheet State
    var showMethodSheet by remember { mutableStateOf(false) }
    var currentActivity by remember { mutableStateOf<LearningActivity?>(null) }
    val sheetState = rememberModalBottomSheetState()
    
    val filteredActivities = remember(activities, selectedTopic, selectedLevel, selectedSyllabus) {
        activities.filter { activity ->
            (selectedTopic == null || activity.topic == selectedTopic) &&
            (selectedLevel == null || activity.level == selectedLevel) &&
            (selectedSyllabus == null || activity.syllabus == selectedSyllabus || activity.syllabus == SyllabusType.COMMON)
        }
    }

    val soundManager = viewModel.soundManager

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Adventure Hub 🗺️", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = { 
                        soundManager.playClick()
                        onNavigateBack() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFF0F9FF)
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Subject Tabs
            ScrollableTabRow(
                selectedTabIndex = if (selectedTopic == null) 0 else LearningTopic.entries.indexOf(selectedTopic) + 1,
                containerColor = Color.Transparent,
                edgePadding = 24.dp,
                divider = {}
            ) {
                Tab(
                    selected = selectedTopic == null,
                    onClick = { 
                        soundManager.playClick()
                        selectedTopic = null 
                    },
                    text = { Text("All Subjects") }
                )
                LearningTopic.entries.forEach { topic ->
                    Tab(
                        selected = selectedTopic == topic,
                        onClick = { 
                            soundManager.playClick()
                            selectedTopic = topic 
                        },
                        text = { Text("${topic.icon} ${topic.displayName}") }
                    )
                }
            }

            // Level Selection
            ScrollableTabRow(
                selectedTabIndex = if (selectedLevel == null) 0 else PrimaryLevel.entries.indexOf(selectedLevel) + 1,
                containerColor = Color.Transparent,
                edgePadding = 24.dp,
                divider = {}
            ) {
                Tab(
                    selected = selectedLevel == null,
                    onClick = { 
                        soundManager.playClick()
                        selectedLevel = null 
                        selectedSyllabus = null
                    },
                    text = { Text("All Levels") }
                )
                PrimaryLevel.entries.forEach { level ->
                    Tab(
                        selected = selectedLevel == level,
                        onClick = { 
                            soundManager.playClick()
                            selectedLevel = level 
                            if (level != PrimaryLevel.P5 && level != PrimaryLevel.P6) {
                                selectedSyllabus = null
                            }
                        },
                        text = { Text(level.name) }
                    )
                }
            }

            // Syllabus Toggle for P5-6
            if (selectedLevel == PrimaryLevel.P5 || selectedLevel == PrimaryLevel.P6) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Stream:", fontWeight = FontWeight.Bold)
                    listOf(SyllabusType.STANDARD, SyllabusType.FOUNDATION).forEach { syllabus ->
                        FilterChip(
                            selected = selectedSyllabus == syllabus,
                            onClick = { 
                                soundManager.playClick()
                                selectedSyllabus = if (selectedSyllabus == syllabus) null else syllabus 
                            },
                            label = { Text(syllabus.displayName) }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(filteredActivities) { activity ->
                    var isVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) { isVisible = true }
                    
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInHorizontally() + fadeIn(animationSpec = tween(500))
                    ) {
                        ActivityCard(
                            activity = activity,
                            onClick = {
                                soundManager.playClick()
                                currentActivity = activity
                                showMethodSheet = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showMethodSheet && currentActivity != null) {
        ModalBottomSheet(
            onDismissRequest = { showMethodSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            MethodSelectionContent(
                activity = currentActivity!!,
                onMethodSelected = { type ->
                    showMethodSheet = false
                    when(type) {
                        ActivityType.QUIZ -> onNavigateToQuiz(currentActivity!!.id)
                        ActivityType.FLASHCARDS -> onNavigateToFlashcards(currentActivity!!.topic, currentActivity!!.id)
                        ActivityType.PUZZLE_MATCH -> onNavigateToPuzzle(currentActivity!!.id)
                        ActivityType.SIMULATION -> onNavigateToSimulation(currentActivity!!.id)
                        ActivityType.WORD_QUEST -> onNavigateToWordQuest(currentActivity!!.id)
                        ActivityType.CLASSIFICATION -> onNavigateToClassification(currentActivity!!.id)
                    }
                }
            )
        }
    }
}

@Composable
fun MethodSelectionContent(
    activity: LearningActivity,
    onMethodSelected: (ActivityType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "How would you like to study?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = activity.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        MethodButton("❓ Quiz Quest", Color(0xFF4FC3F7)) { onMethodSelected(ActivityType.QUIZ) }
        MethodButton("📇 Flashcards", Color(0xFF81C784)) { onMethodSelected(ActivityType.FLASHCARDS) }
        MethodButton("🧩 Memory Puzzle", Color(0xFFFFB74D)) { onMethodSelected(ActivityType.PUZZLE_MATCH) }
        
        if (activity.topic == LearningTopic.MATHEMATICS || activity.topic == LearningTopic.SCIENCE) {
            MethodButton("⚖️ Interactive Lab", Color(0xFFBA68C8)) { 
                if (activity.topic == LearningTopic.SCIENCE && activity.id.startsWith("s1")) {
                    onMethodSelected(ActivityType.CLASSIFICATION)
                } else {
                    onMethodSelected(ActivityType.SIMULATION) 
                }
            }
        }
        
        if (activity.topic == LearningTopic.ENGLISH) {
            MethodButton("✍️ Word Quest", Color(0xFFF06292)) { onMethodSelected(ActivityType.WORD_QUEST) }
        }
    }
}

@Composable
fun MethodButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(64.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ActivityCard(activity: LearningActivity, onClick: () -> Unit) {
    val difficultyColor = when(activity.difficulty) {
        DifficultyLevel.EXPLORER -> Color(0xFF81C784)
        DifficultyLevel.ADVENTURER -> Color(0xFFFFB74D)
        DifficultyLevel.MASTER -> Color(0xFFF06292)
    }

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = difficultyColor.copy(alpha = 0.2f),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(activity.topic.icon, style = MaterialTheme.typography.headlineSmall)
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${activity.level.name}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    if (activity.syllabus != SyllabusType.COMMON) {
                        Text(" • ${activity.syllabus.displayName}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                Text(activity.description, style = MaterialTheme.typography.bodyMedium)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    color = difficultyColor
                ) {
                    Text(
                        text = activity.difficulty.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                contentDescription = null,
                modifier = Modifier.size(24.dp).graphicsLayer { rotationZ = 180f },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
