package com.yowayowa.yawning

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.yowayowa.yawning.databinding.FragmentResultDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultDialogFragment: DialogFragment() {
    private var _binding: FragmentResultDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        // CorrectAnswerDialogインスタンスを生成する
        fun create(combo: Int, distance: Double): ResultDialogFragment {
            return ResultDialogFragment().apply {
                // Bundleを利用してインスタンスに値を渡す
                val bundle = Bundle()
                bundle.putInt("ComboKey", combo)
                bundle.putDouble("DistanceKey", distance)
                arguments = bundle
            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        _binding = FragmentResultDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        //Bundleからの値の取り出し
        arguments?.let{
            binding.comboTextView.text = it.getInt("ComboKey").toString()
            binding.evaluteTextView.text = evaluteText(it.getInt("ComboKey"))
            binding.distanceTextView.text = "${(Math.round(it.getDouble("DistanceKey")) / 100.0)} km"
        }
        binding.button.setOnClickListener{
            activity?.finish()
        }
        return dialog
    }
    private fun evaluteText(n : Int):String{
        return if(n in 0..2) "Soso."
        else if (n in 3..4) "Good!"
        else if (n in 5..6) "Excellent!"
        else if (n in 7..8) "Marvelous!!"
        else "fantastic!!!"
    }
}
