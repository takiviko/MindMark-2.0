package helloworld.mindmark.game.service.runner.normal

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import helloworld.mindmark.GameFragmentDirections
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.common.model.colour.UiColour
import helloworld.mindmark.game.common.model.dto.ScoreDTO
import helloworld.mindmark.game.common.util.UiColourRandomizer
import helloworld.mindmark.game.service.runner.GameModeRunner
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.buttonCount
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.finishCountDownTime
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameSpeed
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameLength

class NormalModeRunner : GameModeRunner {

    private lateinit var binding: FragmentGameBinding

    private val uiColourRandomizer = UiColourRandomizer()
    private val scores: MutableList<Long> = mutableListOf()

    private var gameIsFinished: Boolean = true
    private var clickHappenedInTimeWindow: Boolean = false
    private var timeAtTick: Long = System.currentTimeMillis()
    private var timeAtClick: Long = System.currentTimeMillis()
    private var roundCount: Int = 0

    private lateinit var uiColours: UiColour

    override fun run(binding: FragmentGameBinding) {
        this.binding = binding

        setUp()

        val timer = object: CountDownTimer(gameSpeed * gameLength, gameSpeed) {
            override fun onTick(millisUntilFinished: Long) {
                if (!gameIsFinished) {
                    roundCount++
                    binding.roundCounter.text = getRoundCountText()
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

                val action: NavDirections = GameFragmentDirections.actionGameFragmentToHighScoreFragment(ScoreDTO(scores))

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.root.findNavController().navigate(action)
                }, finishCountDownTime)
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
            val timeElapsed: Long = getTimeElapsedInMillis(button)
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

    private fun getRoundCountText(): String {
        return "$roundCount/$gameLength"
    }

    enum class Button {
        LEFT,
        RIGHT
    }
}