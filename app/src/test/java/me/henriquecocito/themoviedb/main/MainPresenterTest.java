package me.henriquecocito.themoviedb.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;

import me.henriquecocito.themoviedb.main.data.model.Genre;
import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import me.henriquecocito.themoviedb.main.presentation.MainPresenter;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock private MainContract.View view;
    @Mock private MainContract.Domain interactor;

    @Captor
    private ArgumentCaptor<Callback<GenresResponse>> genresResponseCaptor;

    private MainContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new MainPresenter(view, interactor);
    }

    @Test
    public void testLoad_shouldShowItems_whenLoadItemsIsSuccessful() {
        GenresResponse response = new GenresResponse();
        response.setGenres(new ArrayList<Genre>(Arrays.asList(
                new Genre(1, "Action"),
                new Genre(2, "Drama"),
                new Genre(3, "Romance"))
                )
        );

        presenter.load();

        verify(interactor, times(1)).loadGenres(genresResponseCaptor.capture());
        genresResponseCaptor.getValue().onResponse(null, Response.success(response));

        assertEquals(3, response.getGenres().size());
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showItems(response.getGenres());
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowEmptyView_whenLoadItemsIsSuccessfulButHasNotItems() {
        GenresResponse response = new GenresResponse();
        response.setGenres(new ArrayList<Genre>());

        presenter.load();

        verify(interactor, times(1)).loadGenres(genresResponseCaptor.capture());
        genresResponseCaptor.getValue().onResponse(null, Response.success(response));

        assertEquals(0, response.getGenres().size());
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showEmptyView();
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenLoadItemsIsNotSuccessful() {
        String error = "{status_code:500, status_message:\"Not found\"}";
        Response<GenresResponse> response = Response.<GenresResponse>error(500, ResponseBody.create(MediaType.parse(""), error));

        presenter.load();

        verify(interactor, times(1)).loadGenres(genresResponseCaptor.capture());
        genresResponseCaptor.getValue().onResponse(null, response);

        assertFalse(response.isSuccessful());
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenApiReturnsFailure() {
        presenter.load();

        verify(interactor, times(1)).loadGenres(genresResponseCaptor.capture());
        genresResponseCaptor.getValue().onFailure(null, new Throwable("Internal server error"));

        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideErrorView();
        verify(view, times(1)).hideEmptyView();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }
}
