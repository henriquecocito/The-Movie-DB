package me.henriquecocito.themoviedb.base.data;

import java.io.IOException;

import me.henriquecocito.themoviedb.Application;
import me.henriquecocito.themoviedb.BuildConfig;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRepository {

    protected <T> T getAPI(Class<T> javaClass) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .build()
                .create(javaClass);
    }

    private OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        HttpUrl url = request
                                .url()
                                .newBuilder()
                                .addEncodedQueryParameter("api_key", BuildConfig.API_KEY)
                                .addEncodedQueryParameter("language", "en-US")
                                .build();

                        Request newRequest = request
                                .newBuilder()
                                .url(url)
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .cache(new Cache(Application.getContext().getCacheDir(), 1024 * 1024))
                .build();
    }
}
