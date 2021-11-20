package helloworld.mindmark.game.service

import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.service.util.UiColourRandomizer

class GameService {

    private val buttonCount: Int = 2

    private lateinit var binding: FragmentGameBinding
    private val uiColourRandomizer = UiColourRandomizer()

    fun run(binding: FragmentGameBinding) {

        this.binding = binding
        setUp()

    }

    private fun setUp() {

        randomizeUiColours()

        binding.leftButton.setOnClickListener {
            randomizeUiColours()
        }

        binding.rightButton.setOnClickListener {
            randomizeUiColours()
        }
    }

    private fun randomizeUiColours() {
        val uiColours = uiColourRandomizer.getRandomUiColours(buttonCount)
        binding.topPanel.setBackgroundColor(uiColours.topPanelColour.hex)
        binding.leftButton.setBackgroundColor(uiColours.buttonColours[0].hex)
        binding.rightButton.setBackgroundColor(uiColours.buttonColours[1].hex)
    }
}