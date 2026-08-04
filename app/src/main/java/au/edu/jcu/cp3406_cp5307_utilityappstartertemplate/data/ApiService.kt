package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class TriviaResponse(
    @SerializedName("results")
    val results: List<TriviaQuestion>
)

data class TriviaQuestion(
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)

interface ApiService {
    /**
     * Fetches math-themed trivia questions from Open Trivia DB.
     * Category 19 is Mathematics.
     */
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("category") category: Int = 19,
        @Query("type") type: String = "multiple"
    ): TriviaResponse
}