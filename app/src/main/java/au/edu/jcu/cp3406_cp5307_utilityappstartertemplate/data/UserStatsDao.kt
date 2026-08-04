package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStatsDao {
    @Query("SELECT * FROM user_stats ORDER BY completionDate DESC")
    fun getAllStats(): Flow<List<UserStats>>

    @Query("SELECT * FROM user_stats WHERE topic = :topic ORDER BY completionDate DESC")
    fun getStatsByTopic(topic: String): Flow<List<UserStats>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: UserStats)

    @Query("DELETE FROM user_stats")
    suspend fun clearAllStats()
}