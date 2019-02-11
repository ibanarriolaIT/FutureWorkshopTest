/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.details;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.databinding.FragmentBreedDetailsBinding;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;


public class BreedDetailsFragment extends DaggerFragment implements BaseView, BreedDetailsPresenter.View {

    private static final String ARG_BREED = "breed";
    private boolean isFavourite;
    private BreedEntity breedEntity;
    private FragmentBreedDetailsBinding binding;

    @Inject
    BreedDetailsPresenter breedDetailsPresenter;

    @BindView(R.id.fab)
    FloatingActionButton favouriteButton;

    public BreedDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_breed_details, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        breedEntity = bundle.getParcelable(ARG_BREED);
        binding.setBreed(breedEntity);
        ButterKnife.bind(this, view);
        onInit();
        return view;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param breed Parameter 1.
     * @return A new instance of fragment BreedDetailsFragment.
     */
    public static BreedDetailsFragment newInstance(BreedEntity breed) {
        BreedDetailsFragment fragment = new BreedDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREED, breed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setFavourite(boolean isFavourite) {
        changeFavourite(isFavourite);
    }

    @Override
    public void onStatsSuccess(BreedStats breedStats) {
        binding.setStats(breedStats);
    }

    @Override
    public void onStatsError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab)
    public void modifyFavourites(View view) {
        if (isFavourite) {
            breedDetailsPresenter.removeFromFavourites(breedEntity);
        } else {
            breedDetailsPresenter.addToFavourites(breedEntity);
        }
    }

    private void changeFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
        if (isFavourite) {
            favouriteButton.setImageResource(R.drawable.ic_favorite);
        } else {
            favouriteButton.setImageResource(R.drawable.ic_not_favorite);
        }
    }

    @Override
    public void onInit() {
        breedDetailsPresenter.attachView(this);
        breedDetailsPresenter.checkIsFavourite(breedEntity.id);
        breedDetailsPresenter.checkBreedDetails((int) breedEntity.id);
    }
}
