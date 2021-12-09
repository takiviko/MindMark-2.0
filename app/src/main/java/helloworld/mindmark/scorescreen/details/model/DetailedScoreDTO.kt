package helloworld.mindmark.scorescreen.details.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DetailedScoreDTO (
    val scoreId: UUID
): Parcelable