package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.LearningTopic
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens.*
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                EducationalApp()
            }
        }
    }
}

@Composable
fun EducationalApp() {
    val navController = rememberNavController()
    val statsViewModel: StatsViewModel = hiltViewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "landing",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("landing") {
                LandingScreen(
                    onNavigateToActivity = { navController.navigate("activity_selection") },
                    onNavigateToStats = { navController.navigate("stats") },
                    onNavigateToSettings = { navController.navigate("settings") }
                )
            }
            composable("activity_selection") {
                ActivitySelectionScreen(
                    viewModel = statsViewModel,
                    onNavigateToQuiz = { id -> navController.navigate("quiz/$id") },
                    onNavigateToFlashcards = { topic: LearningTopic, id: String -> navController.navigate("flashcards/${topic.name}/$id") },
                    onNavigateToPuzzle = { id -> navController.navigate("puzzle/$id") },
                    onNavigateToSimulation = { id -> navController.navigate("simulation/$id") },
                    onNavigateToWordQuest = { id -> navController.navigate("word_quest/$id") },
                    onNavigateToClassification = { id -> navController.navigate("classification/$id") },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "quiz/{activityId}",
                arguments = listOf(androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                ActivityScreen(
                    viewModel = statsViewModel,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "flashcards/{topicName}/{activityId}",
                arguments = listOf(
                    androidx.navigation.navArgument("topicName") { type = androidx.navigation.NavType.StringType },
                    androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType }
                )
            ) { backStackEntry ->
                val topicName = backStackEntry.arguments?.getString("topicName")
                val activityId = backStackEntry.arguments?.getString("activityId")
                val topic = LearningTopic.valueOf(topicName ?: LearningTopic.MATHEMATICS.name)
                FlashcardScreen(
                    viewModel = statsViewModel,
                    topic = topic,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "puzzle/{activityId}",
                arguments = listOf(androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                PuzzleScreen(
                    viewModel = statsViewModel,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "simulation/{activityId}",
                arguments = listOf(androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                SimulationScreen(
                    viewModel = statsViewModel,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "word_quest/{activityId}",
                arguments = listOf(androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                WordQuestScreen(
                    viewModel = statsViewModel,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                "classification/{activityId}",
                arguments = listOf(androidx.navigation.navArgument("activityId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                ClassificationScreen(
                    viewModel = statsViewModel,
                    activityId = activityId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("stats") {
                StatisticsScreen(
                    viewModel = statsViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
