package helloworld.mindmark.statisticsscreen

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
import helloworld.mindmark.database.entity.Statistics
import helloworld.mindmark.databinding.FragmentStatisticsScreenBinding
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameSpeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.sql.Date
import java.time.Instant
import java.util.*
import java.util.stream.Collectors
import kotlin.Comparator

class StatisticsScreenFragment : Fragment() {

    private var _binding: FragmentStatisticsScreenBinding? = null
    private val binding get() = _binding!!

    private val dbConfig = DBConfig()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleVirtualBackButton()
        handlePhysicalBackButton()

        val args: StatisticsScreenFragmentArgs by navArgs()
        val scores = args.scores.scores
        val totalScore = scores.stream().reduce(0, Long::plus)

        val db = (activity as MainActivity?)?.getDatabase()!!

        val player = db.playerDao().findById(dbConfig.getDefaultPlayerId())
        val score = Score(
            id = UUID.randomUUID(),
            score = totalScore,
            timeStamp = Date.from(Instant.now()),
            playerId = player.id)
        val statistics = getStatistics(score.id, scores)

        runOnSeparateThread {
            Log.d("DB", "Saving score to the database on thread ${Thread.currentThread()}")
            db.scoreDao().saveScore(score)
            db.statisticsDao().saveStatistics(statistics)
        }

        binding.textView2.text =
            "Player name: ${player.name}\n" +
            "Score: $totalScore\n" +
            "Number of rounds: ${statistics.numberOfRounds}\n" +
            "Number of correct clicks: ${statistics.correctClicks}\n" +
            "Average reaction time: ${statistics.averageReactionTime}\n" +
            "Best reaction time: ${statistics.bestReactionTime}\n"
    }

    private fun runOnSeparateThread(runnable: Runnable) {
        GlobalScope.launch(Dispatchers.Default) {
            runnable.run()
        }
    }

    private fun handleVirtualBackButton() {
        binding.statisticsScreenBackButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_StatisticsScreenFragment_to_MenuFragment)
        }
    }

    private fun handlePhysicalBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_StatisticsScreenFragment_to_MenuFragment)
        }
    }

    private fun getStatistics(scoreId: UUID, scores: List<Long>): Statistics {
        val numberOfRounds = scores.size
        val correctClicks = scores.stream()
            .filter{score -> score < gameSpeed}
            .count().toInt()
        val averageReactionTime = scores.stream()
            .filter{score -> score < gameSpeed}
            .collect(Collectors.toList())
            .average().toInt()
        val bestReactionTime = scores.stream()
            .min(Comparator.comparing{it})
            .orElseThrow {RuntimeException("No scores found")}
            .toInt()

        return Statistics(
            scoreId = scoreId,
            numberOfRounds = numberOfRounds,
            correctClicks = correctClicks,
            averageReactionTime = averageReactionTime,
            bestReactionTime = bestReactionTime
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}