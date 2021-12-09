package helloworld.mindmark.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import helloworld.mindmark.R
import helloworld.mindmark.databinding.FragmentMenuBinding
import kotlin.system.exitProcess

/**
 * Fragment class for the main menu.
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishAffinity(requireActivity())
            exitProcess(0)
        }

        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_GameFragment)
        }

        binding.scoreScreenButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_scoreScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}