/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.favorite;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.databinding.FragmentBreedListBinding;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.breeds.adapter.BreedsListAdapter;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsFragment;
import com.futureworkshops.codetest.android.presentation.utils.DetailsTransition;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteBreedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteBreedsFragment extends DaggerFragment
        implements FavouriteBreedsPresenter.View, BreedsListAdapter.OnItemClickListener {

    @Inject
    public FavouriteBreedsPresenter favouriteBreedsPresenter;
    private FragmentBreedListBinding binding;

    public static FavouriteBreedsFragment newInstance() {

        return new FavouriteBreedsFragment();
    }

    public FavouriteBreedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_breed_list, container, false);
        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(R.string.title_favorites);
        toolbar.setDisplayHomeAsUpEnabled(false);
        onInit();
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        favouriteBreedsPresenter.detachView();
    }

    @Override
    public void onInit() {
        favouriteBreedsPresenter.attachView(this);
        favouriteBreedsPresenter.getFavourites();
    }

    @Override
    public void onGetFavourites(List<Breed> breeds) {
        binding.swipeRefreshLayout.setRefreshing(false);
        BreedsListAdapter adapter = new BreedsListAdapter(breeds);
        RecyclerView.LayoutManager listLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerView.setLayoutManager(listLayoutManager);
        adapter.setOnItemClickListener(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMessageItemClick(Breed breed, ImageView view) {
        BreedDetailsFragment breedDetailsFragment = BreedDetailsFragment.newInstance(breed, ViewCompat.getTransitionName(view));
        breedDetailsFragment.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        breedDetailsFragment.setEnterTransition(new Fade());
        setExitTransition(new Fade());
        setSharedElementReturnTransition(new DetailsTransition());
        getFragmentManager().beginTransaction()
                .addSharedElement(view, ViewCompat.getTransitionName(view))
                .replace(R.id.fragmentContainer, breedDetailsFragment, "BREEDS_DETAILS")
                .addToBackStack("FAVORITES_ROOT")
                .commit();
    }
}
