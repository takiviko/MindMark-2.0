package helloworld.mindmark.game.service.runner.normal

import android.os.Handler
import android.os.Looper
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.fragment.GameFragmentDirections
import helloworld.mindmark.game.common.model.colour.UiColour
import helloworld.mindmark.game.common.model.dto.ScoreDTO
import helloworld.mindmark.game.common.util.UiColourRandomizer
import helloworld.mindmark.game.service.runner.GameModeRunner
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.buttonCount
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.finishCountDownTime
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameLength
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameSpeed
import java.util.*

class NormalModeRunner : GameModeRunner {

    private lateinit var binding: FragmentGameBinding

    private val uiColourRandomizer = UiColourRandomizer()
    private val scores: MutableList<Long> = mutableListOf()

    private val mHandler = Handler()
    private val mUiThread: Thread? = null

    private var gameIsFinished: Boolean = true
    private var clickHappenedInTimeWindow: Boolean = true
    private var timeAtTick: Long = System.currentTimeMillis()
    private var timeAtClick: Long = System.currentTimeMillis()
    private var roundCount: Int = 0

    private lateinit var uiColours: UiColour

    override fun run(binding: FragmentGameBinding) {
        this.binding = binding

        setUp()
        gameLoop()

    }

    private fun gameLoop() {
        val timer = Timer()
        val timerTask = object : TimerTask() {

            override fun run() {
                runOnUiThread(kotlinx.coroutines.Runnable {
                    if (roundCount < gameLength) {
                        onTick()
                    } else {
                        onFinish()
                        timer.cancel()
                    }
                })
            }
        }
        timer.schedule(timerTask, gameSpeed, gameSpeed)
        return
    }

    private fun onTick() {
        if (!gameIsFinished) {
            roundCount++
            binding.roundCounter.text = getRoundCountText()

            if (!clickHappenedInTimeWindow) {
                scores.add(gameSpeed)
            }

            clickHappenedInTimeWindow = false
            timeAtTick = System.currentTimeMillis()
            randomizeUiColours()
        }
    }

    fun onFinish() {
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

    private fun setUp() {
        runOnUiThread(kotlinx.coroutines.Runnable {
            this.gameIsFinished = false

            setInitialColours()

            binding.leftButton.setOnClickListener {
                handleButtonClick(Button.LEFT)
            }

            binding.rightButton.setOnClickListener {
                handleButtonClick(Button.RIGHT)
            }
        })
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

    private fun setInitialColours() {

        val textViewText = "Get ready!"
        binding.textView.text = textViewText

        val greyColour = 0x44444444

        binding.topPanel.setBackgroundColor(greyColour)
        binding.leftButton.setBackgroundColor(greyColour)
        binding.rightButton.setBackgroundColor(greyColour)
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

    //From Activity.java
    fun runOnUiThread(action: Runnable) {
        mHandler.post(action)
    }

    enum class Button {
        LEFT,
        RIGHT
    }
}