package com.ubaya.student.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubaya.student.R
import com.ubaya.student.databinding.FragmentStudentDetailBinding
import com.ubaya.student.databinding.FragmentStudentListBinding
import com.ubaya.student.viewmodel.DetailViewModel

class StudentDetailFragment : Fragment() {
    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            student ->
            student?.let {
                binding.txtID.setText(it.id)
                binding.txtName.setText(it.name)
                binding.txtDOB.setText(it.dob)
                binding.txtPhone.setText(it.phone)
            }
        })
    }
}