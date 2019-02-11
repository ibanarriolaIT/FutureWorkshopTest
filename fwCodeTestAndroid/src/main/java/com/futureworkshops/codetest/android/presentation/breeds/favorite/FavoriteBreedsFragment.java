/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsFragment;
import com.futureworkshops.codetest.android.presentation.breeds.adapter.BreedsListAdapter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteBreedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteBreedsFragment extends DaggerFragment
        implements BaseView, FavouriteBreedsPresenter.View, BreedsListAdapter.OnItemClickListener {

    @Inject
    public FavouriteBreedsPresenter favouriteBreedsPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerBreed;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    public static FavoriteBreedsFragment newInstance() {

        return new FavoriteBreedsFragment();
    }

    public FavoriteBreedsFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_breed_list, container, false);
        ButterKnife.bind(this, view);
        onInit();
        return view;
    }

    @Override
    public void onInit() {
        favouriteBreedsPresenter.attachView(this);
        favouriteBreedsPresenter.getFavourites();
    }

    @Override
    public void onGetFavourites(List<Breed> breeds) {
        swipeRefreshLayout.setRefreshing(false);
        BreedsListAdapter adapter = new BreedsListAdapter(breeds);
        RecyclerView.LayoutManager listLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerBreed.setLayoutManager(listLayoutManager);
        adapter.setOnItemClickListener(this);
        recyclerBreed.setAdapter(adapter);
    }

    @Override
    public void onMessageItemClick(Breed breed) {
        BreedEntity breedEntity = new BreedEntity(breed.id(), breed.name(), breed.description(), breed.photoUrl());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, BreedDetailsFragment.newInstance(breedEntity), "BREEDS_ROOT")
                .addToBackStack("BREEDS_ROOT")
                .commit();
    }
}
