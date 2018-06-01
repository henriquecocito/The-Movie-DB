package me.henriquecocito.themoviedb.movies;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import me.henriquecocito.themoviedb.movie.MovieContract;
import me.henriquecocito.themoviedb.movie.data.model.Movie;
import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import me.henriquecocito.themoviedb.movie.presentation.MoviePresenter;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoviePresenterTest {

    @Mock private MovieContract.View view;
    @Mock private MovieContract.Domain interactor;

    @Captor
    private ArgumentCaptor<Callback<MovieResponse>> moviesResponseCaptor;

    private MovieContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new MoviePresenter(view, interactor);
    }

    @Test
    public void testLoad_shouldShowItems_whenLoadItemsIsSuccessful() {
        MovieResponse response = new MovieResponse(123, 1, new ArrayList<Movie>(Arrays.asList(
                new Movie(),
                new Movie(),
                new Movie()
        )));

        presenter.load(123);

        verify(interactor, times(1)).loadMovies(eq(123), eq(1), moviesResponseCaptor.capture());
        moviesResponseCaptor.getValue().onResponse(null, Response.success(response));

        assertEquals(3, response.getResults().size());
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showItems(response.getResults());
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowEmptyView_whenLoadItemsIsSuccessfulButHasNotItems() {
        MovieResponse response = new MovieResponse(123, 1, new ArrayList<Movie>());

        presenter.load(123);

        verify(interactor, times(1)).loadMovies(eq(123), eq(1), moviesResponseCaptor.capture());
        moviesResponseCaptor.getValue().onResponse(null, Response.success(response));

        assertEquals(0, response.getResults().size());
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showEmptyView();
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenLoadItemsIsNotSuccessful() {
        String error = "{status_code:500, status_message:\"Not found\"}";
        Response<MovieResponse> response = Response.<MovieResponse>error(500, ResponseBody.create(MediaType.parse(""), error));

        presenter.load(123);

        verify(interactor, times(1)).loadMovies(eq(123), eq(1), moviesResponseCaptor.capture());
        moviesResponseCaptor.getValue().onResponse(null, response);

        assertFalse(response.isSuccessful());
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenGenreIdIsZero() {
        presenter.load(0);

        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showError(any(Throwable.class));
    }

    @Test
    public void testLoad_shouldShowError_whenApiReturnsFailure() {
        presenter.load(123);

        verify(interactor, times(1)).loadMovies(eq(123), eq(1), moviesResponseCaptor.capture());
        moviesResponseCaptor.getValue().onFailure(null, new Throwable("Internal server error"));

        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }
}
