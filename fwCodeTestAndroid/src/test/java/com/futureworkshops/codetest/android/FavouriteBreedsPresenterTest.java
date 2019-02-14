package com.futureworkshops.codetest.android;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.usecase.GetFavouriteBreeds;
import com.futureworkshops.codetest.android.presentation.breeds.favorite.FavouriteBreedsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavouriteBreedsPresenterTest {

    GetFavouriteBreeds getFavouriteBreeds = mock(GetFavouriteBreeds.class);

    @InjectMocks
    FavouriteBreedsPresenter favouriteBreedsPresenter;

    private final PublishSubject<List<Breed>> listPublishSubject = PublishSubject.create();

    private final Breed breed = Breed.builder().id(1).name("dog").description("test dog").photoUrl("dogPhoto").build();
    private final List<Breed> breeds = Arrays.asList(breed);

    private FavouriteBreedsPresenter.View view = Mockito.mock(FavouriteBreedsPresenter.View.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        favouriteBreedsPresenter.attachView(view);
    }

    @Test
    public void testGetFavourites() {
        when(getFavouriteBreeds.execute()).thenReturn(listPublishSubject.take(1).singleOrError());
        favouriteBreedsPresenter.getFavourites();
        listPublishSubject.onNext(breeds);
        verify(view).onGetFavourites(breeds);
    }

}
