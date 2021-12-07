package helloworld.mindmark.database.entity

import androidx.room.*
import helloworld.mindmark.database.entity.converter.DateConverter
import helloworld.mindmark.database.entity.converter.UUIDConverter
import java.util.Date
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Player::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("playerId"))])

data class Score(
    @PrimaryKey
    @TypeConverters(UUIDConverter::class)
    val id: UUID,

    @ColumnInfo
    val playerId: UUID,

    @ColumnInfo
    val score: Long,

    @ColumnInfo
    @TypeConverters(DateConverter::class)
    val timeStamp: Date

)
