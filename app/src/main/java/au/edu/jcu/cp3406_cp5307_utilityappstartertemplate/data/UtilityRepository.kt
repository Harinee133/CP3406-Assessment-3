package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilityRepository @Inject constructor(
    private val userStatsDao: UserStatsDao,
    private val apiService: ApiService
) {
    fun getAllStats(): Flow<List<UserStats>> = userStatsDao.getAllStats()
    suspend fun insertStats(stats: UserStats) = userStatsDao.insertStats(stats)
    fun getStatsByTopic(topic: LearningTopic) = userStatsDao.getStatsByTopic(topic.name)
    suspend fun clearStats() = userStatsDao.clearAllStats()

    fun getAvailableActivities(): List<LearningActivity> {
        return listOf(
            LearningActivity("m1", LearningTopic.MATHEMATICS, PrimaryLevel.P1, SyllabusType.COMMON, DifficultyLevel.EXPLORER, ActivityType.QUIZ, "Numbers & Bonds", "P1: Counting and number bonds."),
            LearningActivity("m3", LearningTopic.MATHEMATICS, PrimaryLevel.P3, SyllabusType.COMMON, DifficultyLevel.ADVENTURER, ActivityType.SIMULATION, "Math Multi-Step Lab", "P3: Real-world word problems."),
            LearningActivity("m6", LearningTopic.MATHEMATICS, PrimaryLevel.P6, SyllabusType.STANDARD, DifficultyLevel.MASTER, ActivityType.QUIZ, "Algebra & Ratios", "P6: Variables and recipe scaling."),
            
            LearningActivity("e1", LearningTopic.ENGLISH, PrimaryLevel.P1, SyllabusType.COMMON, DifficultyLevel.EXPLORER, ActivityType.WORD_QUEST, "Sight Words", "P1: Match words with pictures."),
            LearningActivity("e3", LearningTopic.ENGLISH, PrimaryLevel.P3, SyllabusType.COMMON, DifficultyLevel.ADVENTURER, ActivityType.QUIZ, "Grammar Lab", "P3: Rearrange jumbled sentences."),
            LearningActivity("e6", LearningTopic.ENGLISH, PrimaryLevel.P6, SyllabusType.STANDARD, DifficultyLevel.MASTER, ActivityType.WORD_QUEST, "Idiom Master", "P6: Match idioms to meanings."),

            LearningActivity("s1", LearningTopic.SCIENCE, PrimaryLevel.P3, SyllabusType.COMMON, DifficultyLevel.EXPLORER, ActivityType.CLASSIFICATION, "Habitats & Life", "P3: Diversity and life cycles."),
            LearningActivity("s3", LearningTopic.SCIENCE, PrimaryLevel.P5, SyllabusType.STANDARD, DifficultyLevel.MASTER, ActivityType.SIMULATION, "Advanced Circuits", "P5: Variables in electrical systems.")
        )
    }

    fun getPuzzleData(activityId: String?): PuzzleData {
        return when {
            // P1-2 (Words/Pictures, Bonds, Habitats)
            activityId?.startsWith("e") == true && activityId <= "e2" -> 
                PuzzleData(listOf(PuzzlePair("Cat", "🐱"), PuzzlePair("Dog", "🐶"), PuzzlePair("Bird", "🐦"), PuzzlePair("Fish", "🐟")), 0xFFF06292)
            activityId?.startsWith("m") == true && activityId <= "m2" -> 
                PuzzleData(listOf(PuzzlePair("3 + 2", "5"), PuzzlePair("4 + 4", "8"), PuzzlePair("10 - 2", "8"), PuzzlePair("1 + 6", "7")), 0xFF81C784)
            activityId?.startsWith("s") == true && activityId <= "s2" -> 
                PuzzleData(listOf(PuzzlePair("Fish", "🌊"), PuzzlePair("Bird", "🌳"), PuzzlePair("Camel", "🌵"), PuzzlePair("Lion", "草原")), 0xFF81C784)

            // P3-4 (Synonyms, Multi Facts, Processes)
            activityId?.startsWith("e") == true && activityId <= "e4" -> 
                PuzzleData(listOf(PuzzlePair("Fast", "Quick"), PuzzlePair("Happy", "Joyful"), PuzzlePair("Large", "Huge"), PuzzlePair("Small", "Tiny")), 0xFFF06292)
            activityId?.startsWith("m") == true && activityId <= "m4" -> 
                PuzzleData(listOf(PuzzlePair("6 x 7", "42"), PuzzlePair("8 x 4", "32"), PuzzlePair("9 x 3", "27"), PuzzlePair("5 x 5", "25")), 0xFF4FC3F7)

            // P5-6 (Idioms, Frac/Dec, Experiments)
            activityId?.startsWith("e") == true -> 
                PuzzleData(listOf(PuzzlePair("Break the ice", "Start conversation"), PuzzlePair("Piece of cake", "Very easy"), PuzzlePair("Under weather", "Feeling sick")), 0xFFF06292)
            activityId?.startsWith("m") == true -> 
                PuzzleData(listOf(PuzzlePair("3/4", "0.75"), PuzzlePair("1/2", "0.5"), PuzzlePair("1/4", "0.25"), PuzzlePair("1/5", "0.2")), 0xFF4FC3F7)
            else -> PuzzleData(listOf(PuzzlePair("Circuit", "💡 Light"), PuzzlePair("Ice + Heat", "💧 Water"), PuzzlePair("Leaves", "🍃 Food")), 0xFFBA68C8)
        }
    }

    fun getSimulationData(activityId: String?): SimulationData {
        return when (activityId) {
            "m3" -> SimulationData("Math Logic Lab 🚌", listOf(
                LabStep("A bus has 40 seats. 3 buses are full. Total?", 120, "passengers"),
                LabStep("If 20 passengers get off, how many left?", 100, "passengers")
            ), 0xFFFFD54F)
            "s3" -> SimulationData("Circuit Lab ⚡", listOf(
                LabStep("Connect 2 batteries (3V each). Total?", 6, "Volts"),
                LabStep("Add a resistor to drop 2V. Current?", 4, "Volts")
            ), 0xFFBA68C8)
            else -> SimulationData("Science Lab 🧪", listOf(
                LabStep("Heat Ice to 0°C. State?", 0, "Solid to Liquid"),
                LabStep("Heat Water to 100°C. State?", 100, "Liquid to Gas")
            ))
        }
    }

    fun getQuestionsForActivity(activityId: String?): List<TriviaQuestion> = listOf(TriviaQuestion("Sample", "A", listOf("B", "C", "D")))
    fun getFlashcardsForActivity(activityId: String?): List<Flashcard> = listOf(Flashcard("Front", "Back"))
    suspend fun getTriviaQuestions(): List<TriviaQuestion> = emptyList()
}
