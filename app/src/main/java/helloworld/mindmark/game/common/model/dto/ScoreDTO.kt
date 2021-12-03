package helloworld.mindmark.game.common.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreDTO (
    val scores: List<Long>
): Parcelable