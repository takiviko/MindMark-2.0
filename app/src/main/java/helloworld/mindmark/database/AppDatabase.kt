package helloworld.mindmark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import helloworld.mindmark.database.dao.PlayerDao
import helloworld.mindmark.database.dao.ScoreDao
import helloworld.mindmark.database.entity.Player
import helloworld.mindmark.database.entity.Score
import helloworld.mindmark.database.entity.converter.DateConverter
import helloworld.mindmark.database.entity.converter.UUIDConverter

@Database(entities = [Player::class, Score::class], version = 1)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "pokemon_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}