package com.yowayowa.yawning.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yowayowa.yawning.databinding.FragmentRankingBinding
import com.yowayowa.yawning.databinding.FragmentRankingComboBinding

class RankingComboFragment : Fragment() {

    private lateinit var rankingViewModel: RankingViewModel
    private var _binding: FragmentRankingComboBinding? = null

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

        _binding = FragmentRankingComboBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rankingViewModel.getComboRankings().observe(viewLifecycleOwner, Observer {
            println(it[1])
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
