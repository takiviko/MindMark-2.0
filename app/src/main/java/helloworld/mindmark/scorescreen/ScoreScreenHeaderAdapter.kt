package helloworld.mindmark.scorescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import helloworld.mindmark.R

class ScoreScreenHeaderAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_score_screen_header, parent, false)) {}

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {}

    override fun getItemCount() = 1
}