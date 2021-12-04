package helloworld.mindmark.database.entity.converter

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun toString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUuid(string: String): UUID {
        return UUID.fromString(string)
    }

}