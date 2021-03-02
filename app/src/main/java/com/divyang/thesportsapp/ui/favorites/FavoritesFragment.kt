package com.divyng.thesportapp.ui.favorites

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.divyang.thesports.ui.favorites.FavoritesViewModel
import com.divyang.thesportsapp.R
import com.divyang.thesportsapp.model.TeamMember
import com.divyang.thesportsapp.model.TeamsAdapter
import com.divyang.thesportsapp.util.hide
import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.android.synthetic.main.main_fragment.recyclerView


class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    private val observer: Observer<List<TeamMember>> = Observer { teams ->
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FavoritesViewModel(
            requireContext().getSharedPreferences(
                "SportDB",
                Context.MODE_PRIVATE
            )
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.teams.observe(viewLifecycleOwner, observer)
        pb_fav.hide()

    }

}


