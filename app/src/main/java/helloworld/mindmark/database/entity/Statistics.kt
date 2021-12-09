package helloworld.mindmark.database.entity

import androidx.room.*
import helloworld.mindmark.database.entity.converter.UUIDConverter
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Score::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("scoreId"))])
data class Statistics(

    @PrimaryKey
    @TypeConverters(UUIDConverter::class)
    val scoreId: UUID,

    @ColumnInfo
    val numberOfRounds: Int,

    @ColumnInfo
    val correctClicks: Int,

    @ColumnInfo
    val averageReactionTime: Int,

    @ColumnInfo
    val bestReactionTime: Int
)