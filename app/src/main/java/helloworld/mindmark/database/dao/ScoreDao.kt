package helloworld.mindmark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import helloworld.mindmark.database.entity.Score
import java.util.*

@Dao
interface ScoreDao {

    @Query("select * from score where id = :id")
    fun findById(id: UUID): Score

    @Query("select * from score")
    fun findAll(): List<Score>

    @Insert
    fun saveScore(score: Score)

}