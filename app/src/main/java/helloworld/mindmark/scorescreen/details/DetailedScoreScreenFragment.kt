package helloworld.mindmark.scorescreen.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import helloworld.mindmark.MainActivity
import helloworld.mindmark.R
import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.databinding.FragmentDetailedScoreScreenBinding
import helloworld.mindmark.databinding.FragmentStatisticsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class DetailedScoreScreenFragment : Fragment() {

    private var _binding: FragmentDetailedScoreScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedScoreScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailedScoreScreenFragmentArgs by navArgs()
        val scoreId = args.detailedScoreItem.scoreId
        val db = (activity as MainActivity?)?.getDatabase()!!

        updateScores(db, scoreId)
    }

    private fun updateScores(db: AppDatabase, scoreId: UUID) = GlobalScope.launch {
        withContext(Dispatchers.Default) {

            val score = db.scoreDao().findById(scoreId)
            val statistics = db.statisticsDao().findByScoreId(scoreId)

            withContext(Dispatchers.Main) {
                val text = "Score: ${score.score}\n" +
                        "Achieved on: ${formatDate(score.timeStamp)}\n" +
                        "Number of rounds: ${statistics.numberOfRounds}\n" +
                        "Number of correct clicks: ${statistics.correctClicks}\n" +
                        "Average reaction time: ${statistics.averageReactionTime}\n" +
                        "Best reaction time: ${statistics.bestReactionTime}\n"

                binding.detailedScoreScreenTextview.text = text
            }
        }
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date)
    }
}