package helloworld.mindmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import helloworld.mindmark.databinding.FragmentHighScoreBinding
import helloworld.mindmark.game.service.runner.normal.NormalModeConfig.Companion.gameSpeed

class HighScoreFragment : Fragment() {

    private var _binding: FragmentHighScoreBinding? = null

    private val binding get() = _binding!!

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

        binding.highScoreScreenBackButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_HighScoreFragment_to_MenuFragment)
        }

        val args: HighScoreFragmentArgs by navArgs()
        val scores = args.scores.scores
        val totalScore = scores.stream().reduce(0, Long::plus)
        val numberOfMisclicks = scores.stream()
            .filter{score -> score == gameSpeed}
            .count()

        binding.textView2.text =
            "Score: $totalScore\n" +
            "Number of misclicks: $numberOfMisclicks"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}