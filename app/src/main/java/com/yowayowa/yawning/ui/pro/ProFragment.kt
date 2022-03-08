package com.yowayowa.yawning.ui.pro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yowayowa.yawning.databinding.FragmentProBinding

class ProFragment : Fragment() {

    private lateinit var proViewModel: ProViewModel
    private var _binding: FragmentProBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        proViewModel =
            ViewModelProvider(this).get(ProViewModel::class.java)

        _binding = FragmentProBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPro
        proViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}