package helloworld.mindmark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import helloworld.mindmark.database.entity.Player
import java.util.*

@Dao
interface PlayerDao {

    @Query("select * from player where uuid = :uuid")
    fun findById(uuid: UUID): Player

    @Query("select * from player")
    fun findAll(): List<Player>

    @Insert
    fun savePlayer(player: Player)

}