package helloworld.mindmark

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import helloworld.mindmark.databinding.ActivityGameBinding

class GameActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}