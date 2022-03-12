package com.yowayowa.yawning.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yowayowa.yawning.R
import com.yowayowa.yawning.databinding.RecyclerViewHeaderBinding

class RecyclerViewHeaderFragment : Fragment() {
    private var _binding: RecyclerViewHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecyclerViewHeaderBinding.inflate(inflater, container, false)
        binding.paramTextView.text = arguments?.getString("param")?:"パラメータ"
        val root: View = binding.root
        return root
    }
}
