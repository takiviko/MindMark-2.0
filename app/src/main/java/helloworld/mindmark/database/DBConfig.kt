package helloworld.mindmark.database

import java.util.*

class DBConfig {

    fun getDefaultPlayerId(): UUID {
        return UUID.fromString("2b423bd8-88cd-421a-8a33-24a419b5de69")
    }

    fun getDefaultPlayerName(): String {
        return "Player 1"
    }
}