package com.futureworkshops.codetest.android;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.usecase.AddFavourite;
import com.futureworkshops.codetest.android.domain.usecase.CheckIsFavourite;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedStats;
import com.futureworkshops.codetest.android.domain.usecase.GetFavouriteStats;
import com.futureworkshops.codetest.android.domain.usecase.RemoveFavourite;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BreedDetailPresenterTest {

    GetBreedStats getBreedStats = Mockito.mock(GetBreedStats.class);
    CheckIsFavourite checkIsFavourite = Mockito.mock(CheckIsFavourite.class);
    AddFavourite addFavourite = Mockito.mock(AddFavourite.class);
    RemoveFavourite removeFavourite = Mockito.mock(RemoveFavourite.class);
    GetFavouriteStats getFavouriteStats = Mockito.mock(GetFavouriteStats.class);
    BreedDetailsPresenter.View view = Mockito.mock(BreedDetailsPresenter.View.class);

    @InjectMocks
    BreedDetailsPresenter breedDetailsPresenter;

    private PublishSubject<Boolean> booleanPublishSubject = PublishSubject.create();
    private PublishSubject<BreedStats> breedStatsPublishSubject = PublishSubject.create();
    private Breed breed = Breed.builder().id(1).name("dog").description("test dog").photoUrl("dogPhoto").build();
    private BreedStats breedStats = new BreedStats(3, 5, 2, 5, 4);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        breedDetailsPresenter.attachView(view);
    }

    @Test
    public void testCheckIsFavourite() {
        when(checkIsFavourite.execute(1)).thenReturn(booleanPublishSubject.take(1).singleOrError());
        breedDetailsPresenter.checkIsFavourite(1);
        booleanPublishSubject.onNext(true);
        verify(view).setFavourite(true, false);
    }

    @Test
    public void testCheckIsNotFavourite() {
        when(checkIsFavourite.execute(1)).thenReturn(booleanPublishSubject.take(1).singleOrError());
        breedDetailsPresenter.checkIsFavourite(1);
        booleanPublishSubject.onNext(false);
        verify(view).setFavourite(false, false);
    }

    @Test
    public void testAddToFavourite() {
        when(addFavourite.execute(breed, breedStats)).thenReturn(Completable.complete());
        breedDetailsPresenter.addToFavourites(breed, breedStats);
        verify(view).setFavourite(true, true);
    }

    @Test
    public void testRemoveFromFavourite() {
        when(removeFavourite.execute(breed, breedStats)).thenReturn(Completable.complete());
        breedDetailsPresenter.removeFromFavourites(breed, breedStats);
        verify(view).setFavourite(false, true);
    }

    @Test
    public void testCheckBreedDetails() {
        when(getBreedStats.execute(1)).thenReturn(breedStatsPublishSubject.take(1).singleOrError());
        breedDetailsPresenter.checkBreedDetails(1);
        breedStatsPublishSubject.onNext(breedStats);
        verify(view).onStatsSuccess(breedStats);
    }

    @Test
    public void testGetFavouriteStats() {
        when(getFavouriteStats.execute(1)).thenReturn(breedStatsPublishSubject.take(1).singleOrError());
        breedDetailsPresenter.getFavouriteStats(1);
        breedStatsPublishSubject.onNext(breedStats);
        verify(view).onStatsSuccess(breedStats);
    }
}
