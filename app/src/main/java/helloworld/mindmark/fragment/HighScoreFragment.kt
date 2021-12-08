package helloworld.mindmark.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import helloworld.mindmark.MainActivity
import helloworld.mindmark.R
import helloworld.mindmark.database.config.DBConfig
import helloworld.mindmark.database.entity.Score
import helloworld.mindmark.databinding.FragmentHighScoreBinding
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameSpeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant
import java.util.*

class HighScoreFragment : Fragment() {

    private var _binding: FragmentHighScoreBinding? = null
    private val binding get() = _binding!!

    private val dbConfig = DBConfig()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleVirtualBackButton()
        handlePhysicalBackButton()

        val args: HighScoreFragmentArgs by navArgs()
        val scores = args.scores.scores
        val totalScore = scores.stream().reduce(0, Long::plus)
        val numberOfMisclicks = scores.stream()
            .filter{score -> score == gameSpeed}
            .count()

        val db = (activity as MainActivity?)?.getDatabase()!!

        val player = db.playerDao().findById(dbConfig.getDefaultPlayerId())
        val score = Score(
            id = UUID.randomUUID(),
            score = totalScore,
            timeStamp = Date.from(Instant.now()),
            playerId = player.id)

        runOnSeparateThread {
            Log.d("DB", "Saving score to the database on thread ${Thread.currentThread()}")
            db.scoreDao().saveScore(score)
        }

        binding.textView2.text =
            "Player name: ${player.name}\n" +
            "Score: $totalScore\n" +
            "Number of misclicks: $numberOfMisclicks"

    }

    private fun runOnSeparateThread(runnable: Runnable) {
        GlobalScope.launch(Dispatchers.Default) {
            runnable.run()
        }
    }

    private fun handleVirtualBackButton() {
        binding.highScoreScreenBackButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_HighScoreFragment_to_MenuFragment)
        }
    }

    private fun handlePhysicalBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_HighScoreFragment_to_MenuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}