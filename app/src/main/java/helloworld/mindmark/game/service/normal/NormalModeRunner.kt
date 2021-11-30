package helloworld.mindmark.game.service.normal

import android.os.CountDownTimer
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.common.util.UiColourRandomizer
import helloworld.mindmark.game.service.runner.GameModeRunner

class NormalModeRunner : GameModeRunner {

    private lateinit var binding: FragmentGameBinding

    private val buttonCount: Int = 2    //The number of buttons that is on the bottom of the screen
    private val gameLength: Int = 10    //The number of rounds to be played
    private val gameSpeed: Long = 2000    //The amount of time between rounds (ms)
    private val uiColourRandomizer = UiColourRandomizer()

    private var gameIsFinished: Boolean = true
    private var clickHappenedInTimeWindow: Boolean = false
    private var timeAtTick: Long = System.currentTimeMillis()
    private var timeAtClick: Long = System.currentTimeMillis()

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
                gameIsFinished = true
                binding.textView.text = "Finished!"
            }
        }

        timer.start()
    }

    private fun loop() {
        clickHappenedInTimeWindow = false
        timeAtTick = System.currentTimeMillis()
        randomizeUiColours()
    }

    private fun setUp() {

        this.gameIsFinished = false

        randomizeUiColours()

        binding.leftButton.setOnClickListener {
            handleButtonClick()
        }

        binding.rightButton.setOnClickListener {
            handleButtonClick()
        }
    }

    private fun handleButtonClick() {
        timeAtClick = System.currentTimeMillis()
        if (!gameIsFinished && !clickHappenedInTimeWindow) {
            clickHappenedInTimeWindow = true
            handleButtonClick()
            printTimeElapsed()
        }
    }

    private fun randomizeUiColours() {
        val uiColours = uiColourRandomizer.getRandomUiColours(buttonCount)
        binding.topPanel.setBackgroundColor(uiColours.topPanelColour.hex)
        binding.leftButton.setBackgroundColor(uiColours.buttonColours[0].hex)
        binding.rightButton.setBackgroundColor(uiColours.buttonColours[1].hex)
        binding.textView.text = uiColours.topPanelColour.name
    }

    private fun printTimeElapsed() {
        this.binding.textView.text = getTimeElapsedInMillis().toString()
    }

    private fun getTimeElapsedInMillis(): Long {
        return timeAtClick - timeAtTick
    }
}