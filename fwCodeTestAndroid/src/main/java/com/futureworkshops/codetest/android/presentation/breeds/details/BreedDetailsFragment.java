/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.breeds.details;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.databinding.FragmentBreedDetailsBinding;
import com.futureworkshops.codetest.android.domain.model.Breed;


public class BreedDetailsFragment extends Fragment {

    private static final String ARG_BREED = "breed";

    public BreedDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBreedDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_breed_details, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        binding.setBreed(bundle.getParcelable(ARG_BREED));
        return view;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param breed Parameter 1.
     * @return A new instance of fragment BreedDetailsFragment.
     */
    public static BreedDetailsFragment newInstance(Breed breed) {
        BreedDetailsFragment fragment = new BreedDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREED, breed);
        fragment.setArguments(args);
        return fragment;
    }


}
