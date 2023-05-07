package com.example.hlc06_enriquefernandez;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hlc06_enriquefernandez.adapters.FavouriteCatAdapter;
import com.example.hlc06_enriquefernandez.apiservice.ApiClient;
import com.example.hlc06_enriquefernandez.apiservice.ICatApiService;
import com.example.hlc06_enriquefernandez.modelo.FavouriteCat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavouriteCatAdapter favouriteCatAdapter;
    private final List<FavouriteCat> favouriteCatList = new ArrayList<>();
    private ICatApiService catApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = findViewById(R.id.recyclerViewFavourites);

        catApiService = ApiClient.getInstance().create(ICatApiService.class);

        favouriteCatAdapter = new FavouriteCatAdapter(favouriteCatList, this, catApiService);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favouriteCatAdapter);

        loadFavouriteCats();
    }

    private void loadFavouriteCats() {
        Call<List<FavouriteCat>> call = catApiService.getFavouriteCats();
        call.enqueue(new Callback<List<FavouriteCat>>() {
            @Override
            public void onResponse(Call<List<FavouriteCat>> call, Response<List<FavouriteCat>> response) {
                if (response.isSuccessful()) {
                    favouriteCatList.clear();
                    favouriteCatList.addAll(response.body());
                    favouriteCatAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FavouritesActivity.this, R.string.failed_to_load_favourites, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FavouriteCat>> call, Throwable t) {
                Toast.makeText(FavouritesActivity.this, R.string.failed_to_load_favourites, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
