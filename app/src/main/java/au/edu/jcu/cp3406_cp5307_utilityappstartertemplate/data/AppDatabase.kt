package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserStats::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userStatsDao(): UserStatsDao
}