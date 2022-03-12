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
import com.yowayowa.yawning.R
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
        setHeader()
        rankingViewModel.getComboRankings().observe(viewLifecycleOwner, Observer {
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = ComboRecycleViewAdapter(it)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setHeader(){
        val ft = fragmentManager?.beginTransaction()
        val fragment = RecyclerViewHeaderFragment()
        val bundle = Bundle()
        bundle.putString("param", "コンボ数")
        fragment.arguments = bundle
        ft?.replace(R.id.comboFragmentContainerView, fragment)?.commit()
    }
}
