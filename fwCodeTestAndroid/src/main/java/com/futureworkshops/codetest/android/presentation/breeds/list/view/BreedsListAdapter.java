package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.databinding.ListItemBreedBinding;
import com.futureworkshops.codetest.android.domain.model.Breed;

import java.util.List;

public class BreedsListAdapter extends RecyclerView.Adapter<BreedsListAdapter.BreedViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Breed> breeds;
    private OnItemClickListener listener;

    public BreedsListAdapter(List<Breed> breeds) {
        this.breeds = breeds;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ListItemBreedBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.list_item_breed, parent, false);
        return new BreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        holder.binding.setBreed(breeds.get(position));
        holder.binding.cardView.setOnClickListener(v -> listener.onMessageItemClick(breeds.get(position)));
    }

    @Override
    public int getItemCount() {
        return breeds.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BreedViewHolder extends RecyclerView.ViewHolder {

        private ListItemBreedBinding binding;

        public BreedViewHolder(ListItemBreedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {

        void onMessageItemClick(Breed breed);
    }
}
