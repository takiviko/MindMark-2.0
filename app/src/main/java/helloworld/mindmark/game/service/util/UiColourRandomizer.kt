package helloworld.mindmark.game.service.util

import helloworld.mindmark.game.data.Colour
import helloworld.mindmark.game.data.UiColour

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