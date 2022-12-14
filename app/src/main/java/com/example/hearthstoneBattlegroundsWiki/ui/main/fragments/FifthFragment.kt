package com.example.hearthstoneBattlegroundsWiki.ui.main.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dota2herowiki.adapter.MyListAdapter
import com.example.dota2herowiki.databinding.FragmentMainBinding
import com.example.dota2herowiki.ui.main.fragments.baseFragment.BaseFragment
import com.example.dota2herowiki.utils.Resource
import com.example.dota2herowiki.viewModel.FifthViewModel
import kotlinx.coroutines.launch

class FifthFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override val viewModel: FifthViewModel by viewModels()
    private val adapter by lazy {
        MyListAdapter()
    }

    override fun initRecycler() {
        binding?.heroRecycler?.adapter = adapter
        adapter.submitList(emptyList())
    }

    override fun httpResponse() {
        viewModel.getContent()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apiResponse.collect {
                    when (it) {
                        is Resource.Error -> {
                            Log.d("error", it.errorData)
                        }
                        is Resource.Loading -> {
                            Log.d("loading", "${it.loader}")
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            adapter.setData(it.data)
                            binding?.progressBar?.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }


}