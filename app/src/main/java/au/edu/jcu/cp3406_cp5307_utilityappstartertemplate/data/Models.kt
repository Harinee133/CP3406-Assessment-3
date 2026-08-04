package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

enum class LearningTopic(val displayName: String, val icon: String) {
    MATHEMATICS("Mathematics", "🔢"),
    ENGLISH("English Language", "📚"),
    SCIENCE("Science", "🧪")
}

enum class SyllabusType(val displayName: String) {
    COMMON("Common"),
    STANDARD("Standard"),
    FOUNDATION("Foundation")
}

enum class PrimaryLevel(val displayName: String) {
    P1("Primary 1"),
    P2("Primary 2"),
    P3("Primary 3"),
    P4("Primary 4"),
    P5("Primary 5"),
    P6("Primary 6")
}

enum class DifficultyLevel {
    EXPLORER, // Easy
    ADVENTURER, // Medium
    MASTER // Hard
}

enum class ActivityType {
    QUIZ,
    FLASHCARDS,
    PUZZLE_MATCH,
    SIMULATION,
    WORD_QUEST,
    CLASSIFICATION
}

data class LearningActivity(
    val id: String,
    val topic: LearningTopic,
    val level: PrimaryLevel,
    val syllabus: SyllabusType,
    val difficulty: DifficultyLevel,
    val type: ActivityType,
    val title: String,
    val description: String
)

data class Flashcard(
    val front: String,
    val back: String
)

data class PuzzlePair(
    val first: String,
    val second: String
)

data class PuzzleData(
    val pairs: List<PuzzlePair>,
    val themeColor: Long = 0xFF64B5F6
)

data class LabStep(
    val instruction: String,
    val targetValue: Int,
    val units: String,
    val hint: String = ""
)

data class SimulationData(
    val title: String,
    val steps: List<LabStep>,
    val themeColor: Long = 0xFFFFD54F
)
