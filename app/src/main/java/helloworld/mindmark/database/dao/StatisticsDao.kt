package helloworld.mindmark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import helloworld.mindmark.database.entity.Statistics
import java.util.*

@Dao
interface StatisticsDao {

    @Insert
    fun saveStatistics(statistics: Statistics)

    @Query("select * from statistics where scoreId = :scoreId")
    fun findByScoreId(scoreId: UUID): Statistics

}