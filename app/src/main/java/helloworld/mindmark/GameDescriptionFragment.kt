package helloworld.mindmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import helloworld.mindmark.databinding.FragmentGameDescriptionBinding

class GameDescriptionFragment : Fragment() {

    private var _binding: FragmentGameDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDescriptionBinding.inflate(layoutInflater)

        val text = "On the game screen you'll find a top panel and two buttons.\n\n" +
                "Each of those will randomly change colour every two seconds.\n\n" +
                "Your goal is to click the button which has the same colour as the top panel.\n\n" +
                "The time between the screen changing and your click will get recorded and added to the scores.\n\n" +
                "Do this 10 times to complete the game.\n\n" +
                "If you don't click or you hit the wrong button, your score gets added 2000 points.\n\n" +
                "Good luck!"

        binding.gameDescriptionTextview.text = text

        return binding.root
    }

}