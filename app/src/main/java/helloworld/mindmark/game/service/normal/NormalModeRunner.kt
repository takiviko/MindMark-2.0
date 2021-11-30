package helloworld.mindmark.game.service.normal

import android.os.CountDownTimer
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.common.model.colour.Colour
import helloworld.mindmark.game.common.model.colour.UiColour
import helloworld.mindmark.game.common.util.UiColourRandomizer
import helloworld.mindmark.game.service.runner.GameModeRunner

class NormalModeRunner : GameModeRunner {

    private lateinit var binding: FragmentGameBinding

    private val buttonCount: Int = 2    //The number of buttons that is on the bottom of the screen
    private val gameLength: Int = 10    //The number of rounds to be played
    private val gameSpeed: Long = 2000    //The amount of time between rounds (ms)
    private val uiColourRandomizer = UiColourRandomizer()
    private val scores: MutableList<Long> = mutableListOf()

    private var gameIsFinished: Boolean = true
    private var clickHappenedInTimeWindow: Boolean = false
    private var timeAtTick: Long = System.currentTimeMillis()
    private var timeAtClick: Long = System.currentTimeMillis()

    private lateinit var uiColours: UiColour

    override fun run(binding: FragmentGameBinding) {
        this.binding = binding

        setUp()

        val timer = object: CountDownTimer(gameSpeed * gameLength, gameSpeed) {
            override fun onTick(millisUntilFinished: Long) {
                if (!gameIsFinished) {
                    loop()
                }
            }

            override fun onFinish() {

                if (!clickHappenedInTimeWindow) {
                    scores.add(gameSpeed)
                }

                gameIsFinished = true
                val text = getFinishedText()
                binding.textView.text = text
            }
        }

        timer.start()
    }

    private fun loop() {

        if (!clickHappenedInTimeWindow) {
            scores.add(gameSpeed)
        }

        clickHappenedInTimeWindow = false
        timeAtTick = System.currentTimeMillis()
        randomizeUiColours()
    }

    private fun setUp() {

        this.gameIsFinished = false

        randomizeUiColours()

        binding.leftButton.setOnClickListener {
            handleButtonClick(Button.LEFT)
        }

        binding.rightButton.setOnClickListener {
            handleButtonClick(Button.RIGHT)
        }
    }

    private fun handleButtonClick(button: Button) {
        timeAtClick = System.currentTimeMillis()

        if (!gameIsFinished && !clickHappenedInTimeWindow) {
            var timeElapsed: Long = getTimeElapsedInMillis(button)
            printTimeElapsed(timeElapsed)
            scores.add(timeElapsed)
            clickHappenedInTimeWindow = true
        }
    }

    private fun randomizeUiColours() {
        this.uiColours = uiColourRandomizer.getRandomUiColours(buttonCount)

        binding.topPanel.setBackgroundColor(uiColours.topPanelColour.hex)
        binding.leftButton.setBackgroundColor(uiColours.buttonColours[0].hex)
        binding.rightButton.setBackgroundColor(uiColours.buttonColours[1].hex)

        binding.textView.text = uiColours.topPanelColour.name
    }

    private fun printTimeElapsed(timeElapsed: Long) {
        val text = "$timeElapsed ms"
        this.binding.textView.text = text
    }

    private fun getTimeElapsedInMillis(button: Button): Long {
        if (isCorrectButtonPressed(button)) {
            return timeAtClick - timeAtTick
        }
        return gameSpeed
    }

    private fun isCorrectButtonPressed(button: Button): Boolean {
        if (this.uiColours.topPanelColour == this.uiColours.buttonColours[button.ordinal]) {
            return true
        }
        return false
    }

    private fun getFinishedText(): String {
        val finalScore: Long = getFinalScore()
        return "Finished!\nScore: $finalScore"
    }

    private fun getFinalScore(): Long {
        return scores.stream().reduce(0L, Long::plus)
    }

    enum class Button {
        LEFT,
        RIGHT
    }
}