package com.example.android_voice_memo.ui.index

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.android_voice_memo.R
import com.example.android_voice_memo.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var mVoiceMemoIndexViewModel: VoiceMemoIndexViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        adapter = ListAdapter(this, 1)
        val recyclerView = view.voice_memo_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val voiceMemo = adapter.getData(position)
                voiceMemo.deleted = true
                Toast.makeText(requireContext(), "メモをゴミ箱へ移しました", Toast.LENGTH_SHORT).show()
                mVoiceMemoIndexViewModel.updateData(voiceMemo)
            }
        })
        helper.attachToRecyclerView(recyclerView)

        mVoiceMemoIndexViewModel = ViewModelProvider(this).get(VoiceMemoIndexViewModel::class.java)
        mVoiceMemoIndexViewModel.readAllData.observe(viewLifecycleOwner, Observer { voiceMemo ->
            adapter.setData(voiceMemo)
        })

        setHasOptionsMenu(true)
        return view
    }

    override fun onItemClick(position: Int) {
        val voiceMemo = adapter.getData(position)
        setFragmentResult("edit_voice_memo", bundleOf(
            "id"      to voiceMemo.id.toString(),
            "content" to voiceMemo.content
        ))
        findNavController().navigate(R.id.action_listFragment_to_editFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    queryPost(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    queryPost(newText)
                    return true
                }

            })
        }
    }

    private fun queryPost(value: String?) {
        if (!TextUtils.isEmpty(value)) {
            val query = value.toString()
            mVoiceMemoIndexViewModel.findByContent(query)
            mVoiceMemoIndexViewModel.readAllData.observe(viewLifecycleOwner, Observer { voiceMemo ->
                adapter.setData(voiceMemo)
            })
        } else {
            mVoiceMemoIndexViewModel.getAll()
            mVoiceMemoIndexViewModel.readAllData.observe(viewLifecycleOwner, Observer { voiceMemo ->
                adapter.setData(voiceMemo)
            })
        }
    }

}
