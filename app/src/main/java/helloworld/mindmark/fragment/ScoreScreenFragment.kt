package helloworld.mindmark.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import helloworld.mindmark.MainActivity
import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.database.DBConfig
import helloworld.mindmark.databinding.FragmentScoreScreenBinding
import helloworld.mindmark.scorescreen.ScoreItemAdapter
import helloworld.mindmark.scorescreen.mapper.ScoreItemMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors

class ScoreScreenFragment : Fragment() {

    private var _binding: FragmentScoreScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private val scoreItemAdapter = ScoreItemAdapter(mutableListOf())

    private val scoreItemMapper = ScoreItemMapper()
    private val dbConfig = DBConfig()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScoreScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = (activity as MainActivity?)?.getDatabase()!!

        val recyclerView = binding.scoreScreenRecycleView

        recyclerView.adapter = scoreItemAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        updateScores(db)
    }

    private fun updateScores(db: AppDatabase) = GlobalScope.launch {
        withContext(Dispatchers.Default) {
            val scores = db.scoreDao().findAll()
            val player = db.playerDao().findById(dbConfig.getDefaultPlayerId())
            val scoreItems = scores.stream()
                .map {
                    scoreItemMapper.mapToScoreItem(it, player)
                }.collect(Collectors.toList())

            withContext(Dispatchers.Main) {
                scoreItemAdapter.addScoreItems(scoreItems)
            }
        }
    }
}