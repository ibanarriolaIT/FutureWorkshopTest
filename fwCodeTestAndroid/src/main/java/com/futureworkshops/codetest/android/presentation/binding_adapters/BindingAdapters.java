package com.futureworkshops.codetest.android.presentation.binding_adapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("photoFilePath")
    public static void loadPhotoFilePath(ImageView itemView, String url) {
        Glide.with(itemView)
                .load(url)
                .into(itemView);
    }
}
