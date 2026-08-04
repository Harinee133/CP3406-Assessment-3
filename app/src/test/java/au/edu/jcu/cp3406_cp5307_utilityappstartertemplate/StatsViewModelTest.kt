package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.*
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.StatsViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StatsViewModelTest {

    private val repository = mockk<UtilityRepository>(relaxed = true)
    private lateinit var viewModel: StatsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
         every { repository.getAllStats() } returns flowOf(listOf(UserStats(score = 50, topic = "MATHEMATICS")))
        every { repository.getAvailableActivities() } returns listOf(
            LearningActivity("1", LearningTopic.MATHEMATICS, PrimaryLevel.P1, SyllabusType.COMMON, DifficultyLevel.EXPLORER, ActivityType.QUIZ, "Test", "Desc")
        )
        viewModel = StatsViewModel(repository, mockk(relaxed = true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test allStats flow emits correct data`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.allStats.collect {} }
        
        val stats = viewModel.allStats.value
        assertEquals(1, stats.size)
        assertEquals(50, stats[0].score)
        assertEquals("MATHEMATICS", stats[0].topic)
        
        collectJob.cancel()
    }

    @Test
    fun `test addStats calls repository insert with correct parameters`() = runTest {
        viewModel.addStats(100, LearningTopic.SCIENCE, DifficultyLevel.MASTER)
        coVerify { 
            repository.insertStats(match { 
                it.score == 100 && it.topic == "SCIENCE" && it.difficulty == "MASTER" 
            }) 
        }
    }
}
