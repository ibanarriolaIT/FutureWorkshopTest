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
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
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

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;


public class BreedDetailsFragment extends DaggerFragment
        implements BreedDetailsPresenter.View {

    private static final String ARG_BREED = "breed";
    private static final String TRANSITION_NAME = "transition_name";
    private boolean isFavourite;
    private Breed breed;
    private FragmentBreedDetailsBinding binding;
    private AnimatedVectorDrawable crossAnimatedDrawable;
    private AnimatedVectorDrawable checkAnimatedDrawable;

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
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        breed = bundle.getParcelable(ARG_BREED);
        binding.setBreed(breed);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_details, breed.name()));
        String transitionName = getArguments().getString(TRANSITION_NAME);
        binding.breedImage.setTransitionName(transitionName);
        ButterKnife.bind(this, view);
        onInit();
        setUpAnimator();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void setFavourite(boolean isFavourite) {
        changeFavourite(isFavourite);
    }

    @Override
    public void onStatsSuccess(BreedStats breedStats) {
        binding.setStats(breedStats);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab)
    public void modifyFavourites(View view) {
        if (isFavourite) {
            breedDetailsPresenter.removeFromFavourites(breed);
        } else {
            breedDetailsPresenter.addToFavourites(breed);
        }
    }

    private void changeFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
        if (isFavourite) {
            binding.fab.setImageDrawable(crossAnimatedDrawable);
            ((AnimatedVectorDrawable) binding.fab.getDrawable()).start();
        } else {
            binding.fab.setImageDrawable(checkAnimatedDrawable);
            ((AnimatedVectorDrawable) binding.fab.getDrawable()).start();
        }
    }

    private void setUpAnimator() {
        crossAnimatedDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.white_heart_animation, getActivity().getTheme());
        checkAnimatedDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.empty_heart_animation, getActivity().getTheme());
    }
}
