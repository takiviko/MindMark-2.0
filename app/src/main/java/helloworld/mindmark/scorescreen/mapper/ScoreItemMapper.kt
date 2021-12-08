package helloworld.mindmark.scorescreen.mapper

import helloworld.mindmark.database.entity.Player
import helloworld.mindmark.database.entity.Score
import helloworld.mindmark.scorescreen.ScoreItem

class ScoreItemMapper {

    fun mapToScoreItem(score: Score, player: Player): ScoreItem {
        return ScoreItem(
            id = score.id,
            playerName = player.name.orEmpty(),
            score = score.score,
            timeStamp = score.timeStamp
        )
    }
}