/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.breeds.adapter.BreedsListAdapter;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsFragment;
import com.futureworkshops.codetest.android.presentation.utils.DetailsTransition;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;


public class BreedsListFragment extends DaggerFragment
        implements BreedsListPresenter.View, BreedsListAdapter.OnItemClickListener {

    @Inject
    BreedsListPresenter breedsListPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerBreed;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static BreedsListFragment newInstance() {
        return new BreedsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_list, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_breeds);
        onInit();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        breedsListPresenter.detachView();
    }

    @Override
    public void onInit() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> breedsListPresenter.getBreedListItems());
        breedsListPresenter.attachView(this);
        breedsListPresenter.getBreedListItems();
    }

    @Override
    public void onGetBreedListItems(List<Breed> breeds) {
        swipeRefreshLayout.setRefreshing(false);
        BreedsListAdapter adapter = new BreedsListAdapter(breeds);
        RecyclerView.LayoutManager listLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerBreed.setLayoutManager(listLayoutManager);
        adapter.setOnItemClickListener(this);
        recyclerBreed.setAdapter(adapter);
    }

    @Override
    public void onError(Throwable error) {
        swipeRefreshLayout.setRefreshing(false);
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
                .replace(R.id.fragmentContainer, breedDetailsFragment, "BREEDS_ROOT")
                .addToBackStack("BREEDS_ROOT")
                .commit();
    }


}
