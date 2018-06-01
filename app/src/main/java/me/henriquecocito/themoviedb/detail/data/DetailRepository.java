package me.henriquecocito.themoviedb.detail.data;

import me.henriquecocito.themoviedb.base.data.BaseRepository;
import me.henriquecocito.themoviedb.detail.DetailContract;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import retrofit2.Call;

public class DetailRepository extends BaseRepository implements DetailContract.Repository {
    @Override
    public Call<DetailResponse> getDetail(int id) {
        return getAPI(DetailDataSource.class).getDetail(id);
    }
}
