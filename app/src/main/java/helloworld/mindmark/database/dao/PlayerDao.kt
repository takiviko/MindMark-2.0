package helloworld.mindmark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import helloworld.mindmark.database.entity.Player
import java.util.*

@Dao
interface PlayerDao {

    @Query("select * from player where id = :id")
    fun findById(id: UUID): Player

    @Query("select * from player")
    fun findAll(): List<Player>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun savePlayer(player: Player)

}