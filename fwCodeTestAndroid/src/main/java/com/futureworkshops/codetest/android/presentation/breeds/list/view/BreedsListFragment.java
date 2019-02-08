/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsFragment;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;


public class BreedsListFragment extends DaggerFragment
        implements BaseView, BreedsListPresenter.View, BreedsListAdapter.OnItemClickListener {

    @Inject
    BreedsListPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerBreed;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static BreedsListFragment newInstance() {
        return new BreedsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_list, container, false);
        ButterKnife.bind(this, view);
        onInit();
        return view;
    }

    @Override
    public void onInit() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getBreedListItems());
        presenter.attachView(this);
        presenter.getBreedListItems();
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


    }

    @Override
    public void onMessageItemClick(Breed breed) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, BreedDetailsFragment.newInstance(breed), "BREEDS_ROOT")
                .commit();
    }
}