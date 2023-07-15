package com.aviral.mindmaze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aviral.mindmaze.databinding.LayoutEachListItemBinding
import com.aviral.mindmaze.models.QuizListModel
import com.bumptech.glide.Glide

class QuizListAdapter(
    private val context: Context,
    private val quizList: List<QuizListModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<QuizListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutEachListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        quizList[position].also {
            holder.binding.quizTitle.text = it.title

            Glide.with(context)
                .load(it.image)
                .into(holder.binding.quizImage)

        }

        holder.binding.quizConstraintLayout.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    inner class ViewHolder(val binding: LayoutEachListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    interface OnItemClickListener {

        fun onItemClick(position: Int)

    }

}