package com.churickoid.characterchat.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.churickoid.characterchat.R
import com.churickoid.characterchat.databinding.ItemMessageBinding
import com.churickoid.characterchat.domain.entity.Message
import com.churickoid.characterchat.domain.entity.MessageOwner

class ChatAdapter(
    var dataSet: List<Message> = emptyList(),
) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(
        val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = dataSet[position]
        with(holder.binding) {
            val layoutParams = cardView.layoutParams as FrameLayout.LayoutParams
            when (message.owner) {
                MessageOwner.USER -> {
                    layoutParams.gravity = Gravity.END
                    cardView.background = holder.itemView.context.getColor(R.color.account_color).toDrawable()
                }
                MessageOwner.SENDER -> {
                    layoutParams.gravity = Gravity.END
                    cardView.background = holder.itemView.context.getColor(R.color.sender_color).toDrawable()
                }
            }
            cardView.layoutParams = layoutParams
            textViewMessage.text = message.text
        }
    }

    override fun getItemCount(): Int = dataSet.size
}