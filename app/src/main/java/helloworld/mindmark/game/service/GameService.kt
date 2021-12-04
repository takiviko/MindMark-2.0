package helloworld.mindmark.game.service

import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.common.model.gamemode.GameMode
import helloworld.mindmark.game.exception.UnknownGameModeException
import helloworld.mindmark.game.service.runner.normal.NormalModeRunner

class GameService {

    private lateinit var binding: FragmentGameBinding
    private val normalModeRunner = NormalModeRunner()

    fun run(binding: FragmentGameBinding, gameMode: GameMode, db: AppDatabase) {

        this.binding = binding

        when (gameMode) {
            GameMode.NORMAL -> normalModeRunner.run(binding, db)
            else -> throw UnknownGameModeException("Unknown game mode: " + gameMode.name)
        }

    }
}