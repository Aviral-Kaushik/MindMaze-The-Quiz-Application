package com.aviral.mindmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aviral.mindmaze.R
import com.aviral.mindmaze.adapters.QuizListAdapter
import com.aviral.mindmaze.databinding.FragmentListBinding
import com.aviral.mindmaze.viewModels.QuizListViewModel


class ListFragment : Fragment(), QuizListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentListBinding

    private lateinit var viewModel: QuizListViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QuizListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.listQuizRecyclerview.apply {

            layoutManager = LinearLayoutManager(context)

            viewModel.getQuizLiveData().observe(viewLifecycleOwner) { value ->

                binding.quizListProgressbar.visibility = View.INVISIBLE

                val listAdapter = QuizListAdapter(context, value, this@ListFragment)

                adapter = listAdapter

                listAdapter.notifyDataSetChanged()

            }

        }
    }

    override fun onItemClick(position: Int) {

        val bundle = Bundle()
        bundle.putInt("position", position)

        navController.navigate(R.id.action_listFragment_to_detailsFragment, bundle)

    }

}