package helloworld.mindmark.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import helloworld.mindmark.database.entity.converter.UUIDConverter
import java.util.*

@Entity
data class Player(

    @TypeConverters(UUIDConverter::class)
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "name")
    val name: String?

)