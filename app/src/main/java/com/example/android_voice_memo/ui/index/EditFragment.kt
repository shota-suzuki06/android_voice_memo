package com.example.android_voice_memo.ui.index

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.android_voice_memo.R
import com.example.android_voice_memo.data.VoiceMemo
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.voice_memo_id
import java.util.*

class EditFragment : Fragment() {

    private lateinit var mVoiceMemoIndexViewModel: VoiceMemoIndexViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        mVoiceMemoIndexViewModel = ViewModelProvider(this).get(VoiceMemoIndexViewModel::class.java)

        view.cancel_btn.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }

        view.save_btn.setOnClickListener {
            updateDate()
        }

        setFragmentResultListener("edit_voice_memo") { resultKey, bundle ->
            val id = bundle.getString("id")
            val content = bundle.getString("content")

            view.voice_memo_id.text = strToEditable(id)
            view.voice_memo_content.text = strToEditable(content)
        }

        return view
    }

    private fun updateDate() {
        val id = voice_memo_id.text.toString()
        val content = voice_memo_content.text.toString()

        if (!TextUtils.isEmpty(content)) {
            val voiceMemo = VoiceMemo(Integer.parseInt(id), content, Date(), false)
            mVoiceMemoIndexViewModel.updateData(voiceMemo)
            Toast.makeText(requireContext(), "メモを保存しました", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "メモを入力してください", Toast.LENGTH_SHORT).show()
        }
    }

    private fun strToEditable(value: String?): Editable {
        return Editable.Factory.getInstance().newEditable(value)
    }

}
