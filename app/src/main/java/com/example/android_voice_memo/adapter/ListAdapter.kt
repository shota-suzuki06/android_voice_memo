package com.example.android_voice_memo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_voice_memo.R
import com.example.android_voice_memo.data.VoiceMemo
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val listener: OnItemClickListener, private val key: Int): RecyclerView.Adapter<ListAdapter.VoiceMemoViewHolder>() {

    private var voiceMemoList = emptyList<VoiceMemo>()

    inner class VoiceMemoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.list_item_layout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceMemoViewHolder {
        return VoiceMemoViewHolder(LayoutInflater.from(parent.context).inflate(if(key == 1)R.layout.list_item else R.layout.delete_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return voiceMemoList.size
    }

    override fun onBindViewHolder(holder: VoiceMemoViewHolder, position: Int) {
        val currentItem = voiceMemoList[position]
        holder.itemView.title_view.text = currentItem.content.substring(0, getTitle(currentItem.content))
        holder.itemView.date_view.text = dateToString(currentItem.date)
    }

    fun setData(voiceMemo: List<VoiceMemo>) {
        this.voiceMemoList = voiceMemo
        notifyDataSetChanged()
    }

    fun getData(position: Int): VoiceMemo {
        return this.voiceMemoList[position]
    }

    fun clearData() {
        this.voiceMemoList = emptyList()
    }

    private fun dateToString(date: Date): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(date) + " のメモ"
    }

    private fun getTitle(value: String): Int {
        val size = value.length
        return if (size <= 30) size else 38
    }

}
