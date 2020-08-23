package com.example.android_voice_memo.ui.voicememo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.android_voice_memo.R
import com.example.android_voice_memo.data.VoiceMemo
import kotlinx.android.synthetic.main.fragment_voice_memo.*
import kotlinx.android.synthetic.main.fragment_voice_memo.view.*
import kotlinx.android.synthetic.main.fragment_voice_memo.view.voice_memo_content
import java.text.SimpleDateFormat
import java.util.*

private const val SPEECH_REQUEST_CODE = 0

class VoiceMemoFragment : Fragment() {

    private lateinit var mVoiceMemoRecViewModel: VoiceMemoRecViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voice_memo, container, false)

        mVoiceMemoRecViewModel = ViewModelProvider(this).get(VoiceMemoRecViewModel::class.java)

        view.rec_btn.setOnClickListener {
            displaySpeechRecognizer()
        }

        view.clear_btn.setOnClickListener {
            view.voice_memo_content.text = null
        }

        view.save_btn.setOnClickListener {
            insertData()
        }

        return view
    }

    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results[0]
                }
            view?.voice_memo_content?.text = strToEditable(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun strToEditable(value: String?): Editable {
        return Editable.Factory.getInstance().newEditable(value)
    }

    private fun insertData() {
        val content = voice_memo_content.text.toString()
        if (!TextUtils.isEmpty(content)) {
            val voiceMemo = VoiceMemo(0, content, Date(), false)
            mVoiceMemoRecViewModel.insertDate(voiceMemo)
            Toast.makeText(requireContext(), "メモを登録しました", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_voice_memo_to_navigation_list)
        } else {
            Toast.makeText(requireContext(), "メモを入力して下さい", Toast.LENGTH_SHORT).show()
        }
    }

}
