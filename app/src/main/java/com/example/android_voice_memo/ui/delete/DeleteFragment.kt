package com.example.android_voice_memo.ui.delete

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.android_voice_memo.R
import com.example.android_voice_memo.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_delete.view.*

class DeleteFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var mVoiceMemoDeleteViewModel: VoiceMemoDeleteViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delete, container, false)

        adapter = ListAdapter(this, 2)
        val recyclerView = view.voice_memo_delete_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mVoiceMemoDeleteViewModel = ViewModelProvider(this).get(VoiceMemoDeleteViewModel::class.java)
        mVoiceMemoDeleteViewModel.readAllDeleteData.observe(viewLifecycleOwner, Observer { voiceMemo ->
            adapter.setData(voiceMemo)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val voiceMemo = adapter.getData(position)
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("復元") { _, _ ->
            voiceMemo.deleted = false
            mVoiceMemoDeleteViewModel.updateData(voiceMemo)
            Toast.makeText(requireContext(), "復元しました", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("キャンセル") { _, _ -> }
        builder.setNeutralButton("削除") { _, _ ->
            mVoiceMemoDeleteViewModel.deleteData(voiceMemo)
            Toast.makeText(requireContext(), "削除しました", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("メモを復元しますか？")
        builder.setMessage("メモを復元する場合は復元ボタンを、削除する場合は削除ボタン押してください")
        builder.create().show()
    }

}
