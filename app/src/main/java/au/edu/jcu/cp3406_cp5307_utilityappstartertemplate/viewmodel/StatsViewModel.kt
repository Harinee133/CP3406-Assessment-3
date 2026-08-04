package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.*
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: UtilityRepository,
    val soundManager: SoundManager
) : ViewModel() {

    private val _questions = MutableStateFlow<List<TriviaQuestion>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _activities = MutableStateFlow<List<LearningActivity>>(emptyList())
    val activities = _activities.asStateFlow()

    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards = _flashcards.asStateFlow()

    private val _puzzleData = MutableStateFlow<PuzzleData?>(null)
    val puzzleData = _puzzleData.asStateFlow()

    private val _simulationData = MutableStateFlow<SimulationData?>(null)
    val simulationData = _simulationData.asStateFlow()

    init {
        _activities.value = repository.getAvailableActivities()
    }

    fun loadPuzzleData(activityId: String?) {
        _puzzleData.value = repository.getPuzzleData(activityId)
    }

    fun loadSimulationData(activityId: String?) {
        _simulationData.value = repository.getSimulationData(activityId)
    }

    fun loadFlashcards(topic: LearningTopic, activityId: String? = null) {
        viewModelScope.launch {
            _flashcards.value = repository.getFlashcardsForActivity(activityId)
        }
    }

    val allStats: StateFlow<List<UserStats>> = repository.getAllStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun fetchQuestions(activityId: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _questions.value = repository.getQuestionsForActivity(activityId)
            _isLoading.value = false
        }
    }

    fun addStats(score: Int, topic: LearningTopic = LearningTopic.MATHEMATICS, difficulty: DifficultyLevel = DifficultyLevel.EXPLORER) {
        viewModelScope.launch {
            repository.insertStats(UserStats(score = score, topic = topic.name, difficulty = difficulty.name))
        }
    }

    fun clearStats() {
        viewModelScope.launch {
            repository.clearStats()
        }
    }
}