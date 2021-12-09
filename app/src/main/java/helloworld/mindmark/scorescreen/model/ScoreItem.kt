package helloworld.mindmark.scorescreen.model

import java.util.*

class ScoreItem(
    val id: UUID,
    val playerName: String,
    val score: Long,
    val timeStamp: Date
)