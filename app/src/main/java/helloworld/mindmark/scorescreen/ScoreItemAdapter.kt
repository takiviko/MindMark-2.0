package helloworld.mindmark.scorescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import helloworld.mindmark.R
import java.text.SimpleDateFormat
import java.util.*

class ScoreItemAdapter(private val dataSet: MutableList<ScoreItem>) : ListAdapter<ScoreItem, ScoreItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ScoreItem> = object : DiffUtil.ItemCallback<ScoreItem>() {
            override fun areItemsTheSame(oldItem: ScoreItem, newItem: ScoreItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ScoreItem, newItem: ScoreItem): Boolean {
                return (oldItem.playerName == newItem.playerName &&
                        (oldItem.score == newItem.score) &&
                        (oldItem.timeStamp == newItem.timeStamp))
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerIdItem: TextView = itemView.findViewById(R.id.score_item_player_id)
        val scoreItem: TextView = itemView.findViewById(R.id.score_item_score)
        val timestampItem: TextView = itemView.findViewById(R.id.score_item_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val scoreView = inflater.inflate(R.layout.fragment_score_item, parent, false)

        return ViewHolder(scoreView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val score: ScoreItem = getItem(position)

        viewHolder.playerIdItem.text = score.playerName
        viewHolder.scoreItem.text = score.score.toString()
        viewHolder.timestampItem.text = formatDate(score.timeStamp)
    }

    fun addScoreItems(scoreItems: List<ScoreItem>) {
        dataSet.clear()
        dataSet.addAll(scoreItems)
        submitList(dataSet)
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date)
    }
}