/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation.landing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemReselectedListener;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.presentation.breeds.favorite.FavoriteBreedsFragment;
import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListFragment;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity implements
        OnNavigationItemSelectedListener,
        OnNavigationItemReselectedListener,
        MainPresenter.View,
        BaseView {

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;

    private BreedsListFragment breedsListFragment;
    private FavoriteBreedsFragment favoriteBreedsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onInit();
        initBottomNavigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.important_action:
                mainPresenter.callImportantOperation();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_breeds:
                Timber.d("select breeds");
                showBreedsFragment();
                break;
            case R.id.action_show_favorites:
                Timber.d("select favorites");
                showFavoritesFragment();
                break;
        }

        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_breeds:
                Timber.d("reselect breeds");
                break;
            case R.id.action_show_favorites:
                Timber.d("reselect favorites");
                break;
        }
    }

    private void showFavoritesFragment() {
        if (favoriteBreedsFragment == null) {
            favoriteBreedsFragment = FavoriteBreedsFragment.newInstance();
        }

        replaceFragment(favoriteBreedsFragment, "FAVORITES_ROOT");
    }

    private void showBreedsFragment() {
        if (breedsListFragment == null) {
            breedsListFragment = BreedsListFragment.newInstance();

        }

        replaceFragment(breedsListFragment, "BREEDS_ROOT");
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }

    private void initBottomNavigation() {
        final Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            final MenuItem item = menu.getItem(i);

            if (item.getItemId() == R.id.action_show_breeds) {
                item.setChecked(true);
                bottomNavigationView.setSelectedItemId(item.getItemId());
                showBreedsFragment();
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

    }

    @Override
    public void onImportantOperationOk() {
        //it will never be ok
    }

    @Override
    public void onImportantOperationError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInit() {
        mainPresenter.attachView(this);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }
}
