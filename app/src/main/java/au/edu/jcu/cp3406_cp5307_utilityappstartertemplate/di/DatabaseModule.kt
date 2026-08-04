package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.di

import android.content.Context
import androidx.room.Room
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.AppDatabase
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.UserStatsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "utility_app_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUserStatsDao(database: AppDatabase): UserStatsDao {
        return database.userStatsDao()
    }
}