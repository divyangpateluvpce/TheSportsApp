package com.divyang.thesportsapp.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.divyang.thesportsapp.R
import com.divyang.thesportsapp.model.TeamMember
import com.divyang.thesportsapp.model.TeamsAdapter
import com.divyang.thesportsapp.util.hide
import com.divyang.thesportsapp.util.show
import kotlinx.android.synthetic.main.main_fragment.*


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private val observer: Observer<List<TeamMember>> = Observer { teams ->
        pb_main.hide()
        recyclerView.adapter =
            TeamsAdapter(
                teams ?: emptyList()
            ) { position ->
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage(teams[position].description)
                    .setCancelable(false)
                    .setNegativeButton("Close", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle(teams[position].name)
                alert.show()
            }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun respondToSearch() {
        viewModel.searchTeams(editTextTextPersonName.text.toString())
        pb_main.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(SearchViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.teams.observe(viewLifecycleOwner, observer)

        button.setOnClickListener { respondToSearch() }

        editTextTextPersonName.setOnEditorActionListener { _, _, _ ->
            respondToSearch()
            return@setOnEditorActionListener true
        }
    }
}


