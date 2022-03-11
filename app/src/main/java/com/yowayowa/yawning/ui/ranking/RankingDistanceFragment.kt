package com.yowayowa.yawning.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yowayowa.yawning.databinding.FragmentRankingDistanceBinding

class RankingDistanceFragment : Fragment() {

    private lateinit var rankingViewModel: RankingViewModel
    private var _binding: FragmentRankingDistanceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rankingViewModel =
            ViewModelProvider(this).get(RankingViewModel::class.java)

        _binding = FragmentRankingDistanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rankingViewModel.getDistanceRankings().observe(viewLifecycleOwner, Observer {
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = DistanceRecycleViewAdapter(it)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
