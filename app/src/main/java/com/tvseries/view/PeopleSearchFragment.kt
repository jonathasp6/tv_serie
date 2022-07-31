package com.tvseries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tvseries.R
import com.tvseries.databinding.FragmentPeopleSearchBinding
import com.tvseries.model.PeopleSearched
import com.tvseries.view.adapter.PeopleSearchedAdapter
import com.tvseries.view.adapter.eventClickPeople

import com.tvseries.viewmodel.PeopleSearchViewModel
import kotlinx.coroutines.*

class PeopleSearchFragment : Fragment() {
    private val peopleSearchViewModel: PeopleSearchViewModel by viewModels()
    private lateinit var binding: FragmentPeopleSearchBinding
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleSearchBinding.inflate(inflater, container, false)

        val tvSeriesAdapter = PeopleSearchedAdapter(clickItem)
        binding.fragmentPeopleSearchRvList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.fragmentPeopleSearchRvList.adapter = tvSeriesAdapter

        peopleSearchViewModel.peopleList.observe(viewLifecycleOwner) {
            it.let {
                tvSeriesAdapter.submitList(it as MutableList<PeopleSearched>)
                if (it.isEmpty()) {
                    Toast.makeText(
                        context,
                        getText(R.string.fragment_people_not_found),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        configSearchPeople()

        return binding.root
    }

    private fun configSearchPeople() {
        binding.fragmentSearchPeopleEtName.addTextChangedListener {
            searchPost()
        }
    }

    private val clickItem: eventClickPeople = { person ->
        onClickListItem(person)
    }

    private fun searchPost() {
        job?.cancel()
        job = lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                delay(300)
                val name = binding.fragmentSearchPeopleEtName.text.toString()
                if (name.isNotEmpty()) {
                    peopleSearchViewModel.loadPerson(requireContext(), name)
                }
            }
        }
    }

    private fun onClickListItem(peopleSearched: PeopleSearched) {
        val bundle = Bundle()
        bundle.putInt("id", peopleSearched.person.id)
        view?.findNavController()?.navigate(R.id.action_navigation_people_to_personFragment, bundle)
    }
}