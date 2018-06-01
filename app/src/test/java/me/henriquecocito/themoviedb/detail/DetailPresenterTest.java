package me.henriquecocito.themoviedb.detail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import me.henriquecocito.themoviedb.detail.DetailContract;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import me.henriquecocito.themoviedb.detail.presentation.DetailPresenter;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock private DetailContract.View view;
    @Mock private DetailContract.Domain interactor;

    @Captor private ArgumentCaptor<Callback<DetailResponse>> detailResponseCaptor;

    private DetailContract.Presenter presenter;
    private DetailResponse response = new DetailResponse(
            "FastShop",
            "",
            new Date(),
            9.5f,
            "/fastshop.png",
            "https://fastshop.com.br");

    @Before
    public void setUp() {
        presenter = new DetailPresenter(view, interactor);
    }

    @Test
    public void testLoad_shouldShowItems_whenLoadItemsIsSuccessful() {
        presenter.load(123);

        verify(interactor, times(1)).loadDetail(eq(123), detailResponseCaptor.capture());
        detailResponseCaptor.getValue().onResponse(null, Response.success(response));

        verify(view, times(1)).showLoading();
        verify(view, times(1)).showDetail(response);
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenLoadItemsIsNotSuccessful() {
        String error = "{status_code:500, status_message:\"Not found\"}";
        Response<DetailResponse> errorResponse = Response.<DetailResponse>error(500, ResponseBody.create(MediaType.parse(""), error));

        presenter.load(123);

        verify(interactor, times(1)).loadDetail(eq(123), detailResponseCaptor.capture());
        detailResponseCaptor.getValue().onResponse(null, errorResponse);

        verify(view, times(1)).showLoading();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testLoad_shouldShowError_whenApiReturnsFailure() {
        presenter.load(123);

        verify(interactor, times(1)).loadDetail(eq(123), detailResponseCaptor.capture());
        detailResponseCaptor.getValue().onFailure(null, new Throwable("Internal server error"));

        verify(view, times(1)).showLoading();
        verify(view, times(1)).showError(any(Throwable.class));
        verify(view, times(1)).hideLoading();
    }
}
