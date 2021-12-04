package helloworld.mindmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import helloworld.mindmark.database.AppDatabase
import helloworld.mindmark.databinding.FragmentGameBinding
import helloworld.mindmark.game.common.model.gamemode.GameMode
import helloworld.mindmark.game.service.GameService

/**
 * Fragment class for the game screen.
 */
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val gameService: GameService = GameService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //To be passed from the previous screen
        val gameType = GameMode.NORMAL

        val db = AppDatabase.getInstance(requireNotNull(this.activity).application)

        gameService.run(binding, gameType, db)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}