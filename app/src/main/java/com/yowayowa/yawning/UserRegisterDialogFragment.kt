package com.yowayowa.yawning

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.yowayowa.yawning.databinding.FragmentUserRegisterDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        binding.button.setOnClickListener{
            GlobalScope.launch{
                val name = binding.editTextUserName.text.toString()
                val password = binding.editTextPassword.text.toString()
                val result = HttpClient().register(name,password)
                if(result != null){
                    startMainActivity()
                    PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().apply(){
                        putString("userName",name)
                        putInt("userID",result)
                        apply()
                    }
                }
            }
        }
        return dialog
    }

    private fun startMainActivity(){
        val intent = Intent(context,MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity?.startActivity(intent)
    }
}
