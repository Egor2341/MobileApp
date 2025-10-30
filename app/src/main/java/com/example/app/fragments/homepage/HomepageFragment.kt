package com.example.app.fragments.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.MainActivity
import com.example.app.data.Cars
import com.example.app.databinding.FragmentHomepageBinding
import com.example.app.databinding.FragmentSignUp1Binding
import com.example.app.fragments.sign_up.FirstSignUpFragment

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        val recyclerView: RecyclerView = binding.rwList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CarAdapter(Cars.cars)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomepageFragment()
    }

}