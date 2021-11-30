package helloworld.mindmark.game.common.util

import helloworld.mindmark.game.common.model.colour.Colour
import helloworld.mindmark.game.common.model.colour.UiColour

class UiColourRandomizer {

    fun getRandomUiColours(size: Int): UiColour {
        val buttonColours: List<Colour> =
            Colour.values().toList()
                .shuffled()
                .take(size)


        return UiColour(
            buttonColours,
            buttonColours.random()
        )
    }
}