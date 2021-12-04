package helloworld.mindmark.game.service.runner

import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.databinding.FragmentGameBinding

interface GameModeRunner {

    fun run(binding: FragmentGameBinding, db: AppDatabase)

}