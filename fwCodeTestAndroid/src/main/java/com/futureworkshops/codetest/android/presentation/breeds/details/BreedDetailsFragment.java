/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.details;


import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.databinding.FragmentBreedDetailsBinding;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.model.BreedStats;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class BreedDetailsFragment extends DaggerFragment
        implements BreedDetailsPresenter.View {

    private static final String ARG_BREED = "breed";
    private static final String TRANSITION_NAME = "transition_name";
    private boolean isFavourite;
    private Breed breed;
    private FragmentBreedDetailsBinding binding;
    private AnimatedVectorDrawable favouriteAnimatedDrawable;
    private AnimatedVectorDrawable nonFavouriteAnimatedDrawable;

    @Inject
    BreedDetailsPresenter breedDetailsPresenter;

    public BreedDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param breed Parameter 1.
     * @return A new instance of fragment BreedDetailsFragment.
     */
    public static BreedDetailsFragment newInstance(Breed breed, String transitionName) {
        BreedDetailsFragment fragment = new BreedDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREED, breed);
        args.putString(TRANSITION_NAME, transitionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_breed_details, container, false);
        Bundle bundle = getArguments();
        breed = bundle.getParcelable(ARG_BREED);
        binding.setBreed(breed);
        String transitionName = getArguments().getString(TRANSITION_NAME);
        binding.breedImage.setTransitionName(transitionName);
        onInit();
        setUpFavourite();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(getString(R.string.title_details, breed.name()));
        toolbar.setDisplayHomeAsUpEnabled(true);

        //When glide loads the image start with the transition
        Glide.with(getContext())
                .load(breed.photoUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(binding.breedImage);
    }

    @Override
    public void onPause() {
        super.onPause();
        breedDetailsPresenter.detachView();
    }

    @Override
    public void onInit() {
        breedDetailsPresenter.attachView(this);
        breedDetailsPresenter.checkIsFavourite(breed.id());
        breedDetailsPresenter.checkBreedDetails(breed.id());
    }

    @Override
    public void setFavourite(boolean isFavourite, boolean informUser) {
        this.isFavourite = isFavourite;
        String message;
        if (isFavourite) {
            binding.fab.setImageDrawable(favouriteAnimatedDrawable);
            ((AnimatedVectorDrawable) binding.fab.getDrawable()).start();
            message = getString(R.string.added_favourite);
        } else {
            binding.fab.setImageDrawable(nonFavouriteAnimatedDrawable);
            ((AnimatedVectorDrawable) binding.fab.getDrawable()).start();
            message = getString(R.string.remove_favourite);
        }
        if (informUser) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStatsSuccess(BreedStats breedStats) {
        binding.setStats(breedStats);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    public void modifyFavourites(View view) {
        if (isFavourite) {
            breedDetailsPresenter.removeFromFavourites(breed);
        } else {
            breedDetailsPresenter.addToFavourites(breed);
        }
    }

    //animations for the favourite button
    private void setUpFavourite() {
        binding.fab.setOnClickListener(this::modifyFavourites);
        favouriteAnimatedDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.white_heart_animation, getActivity().getTheme());
        nonFavouriteAnimatedDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.empty_heart_animation, getActivity().getTheme());
    }
}
