package com.yowayowa.yawning.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yowayowa.yawning.MainViewModel
import com.yowayowa.yawning.MapActivity
import com.yowayowa.yawning.R
import com.yowayowa.yawning.SettingsActivity
import com.yowayowa.yawning.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        activity?.run{
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val image: ImageButton = binding.imageButton
        image.setOnClickListener{
            val intent =  Intent(context,MapActivity::class.java)
            intent.putExtra("lat",mainViewModel.myLocate.value?.latitude)
            intent.putExtra("long",mainViewModel.myLocate.value?.longitude)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
