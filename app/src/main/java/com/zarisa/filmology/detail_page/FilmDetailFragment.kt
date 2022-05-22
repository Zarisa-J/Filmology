package com.zarisa.filmology.detail_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zarisa.filmology.R
import com.zarisa.filmology.databinding.FragmentFilmDetailBinding
import com.zarisa.filmology.main_page.ApiStatus
import com.zarisa.filmology.main_page.filmID


class FilmDetailFragment : Fragment() {
    val viewModel: DetailPageViewModel by viewModels()
    lateinit var binding: FragmentFilmDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        primarySetup()
    }

    private fun primarySetup() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getFilmDetails(requireArguments().getInt(filmID, 0))
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.imageViewState.let { imageView ->
                        imageView.visibility = View.VISIBLE
                        imageView.setImageResource(R.drawable.big_loading_animation)
                    }
                    binding.fabPlay.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    binding.imageViewState.visibility = View.GONE
                    binding.fabPlay.visibility = View.VISIBLE
                }
                ApiStatus.ERROR -> {
                    binding.imageViewState.let { imageView ->
                        imageView.visibility = View.VISIBLE
                        imageView.setImageResource(R.drawable.ic_connection_error)
                    }
                    binding.fabPlay.visibility = View.GONE
                }
                else -> {}
            }
        }
    }
}