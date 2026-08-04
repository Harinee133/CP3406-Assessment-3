package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int = 0,
    val topic: String = "General",
    val difficulty: String = "Explorer",
    val completionDate: Long = System.currentTimeMillis()
)