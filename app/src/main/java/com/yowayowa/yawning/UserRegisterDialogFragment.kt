package com.yowayowa.yawning

import android.animation.ObjectAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.yowayowa.yawning.databinding.FragmentUserRegisterDialogBinding

class UserRegisterDialogFragment : DialogFragment() {

    private var _binding: FragmentUserRegisterDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        // CorrectAnswerDialogインスタンスを生成する
        fun create(): UserRegisterDialogFragment {
            return UserRegisterDialogFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        _binding = FragmentUserRegisterDialogBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        return dialog
    }
}
