package com.tvseries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso
import com.tvseries.R
import com.tvseries.databinding.FragmentPersonBinding
import com.tvseries.model.CastCredit
import com.tvseries.view.adapter.CastCreditAdapter
import com.tvseries.viewmodel.PersonViewModel

class PersonFragment : Fragment() {
    private var idPerson = -1
    private var name = ""
    private val personViewModel: PersonViewModel by viewModels()
    private lateinit var binding: FragmentPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idPerson = it.getInt("id")
        }
        context?.let {
            personViewModel.loadPersonInformation(it, idPerson)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        val castAdapter = CastCreditAdapter {
                credit -> adapterOnClick(credit) }

        binding.fragmentPersonRvList.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        binding.fragmentPersonRvList.adapter = castAdapter

        personViewModel.castCredit.observe(viewLifecycleOwner) {
            castAdapter.submitList(it)
            if (it.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.fragment_person_cast_not_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        personViewModel.person.observe(viewLifecycleOwner) {
            name = it.name
            binding.fragmentPersonInformationTvTitle.text = name

            it.image?.medium.let { image ->
                Picasso.get()
                    .load(image)
                    .into(binding.fragmentPersonInformationIvPoster)
            }
        }

        return binding.root
    }

    private fun adapterOnClick(castCredit: CastCredit) {
        val bundle = Bundle()
        castCredit._embedded?.show?.let { bundle.putInt("id", it.id) }
        view?.findNavController()?.navigate(R.id.action_personFragment_to_tvSeriesFragment, bundle)
    }
}