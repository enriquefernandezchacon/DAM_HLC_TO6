package com.example.hlc06_enriquefernandez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.hlc06_enriquefernandez.adapters.CatAdapter;
import com.example.hlc06_enriquefernandez.apiservice.ApiClient;
import com.example.hlc06_enriquefernandez.apiservice.ICatApiService;
import com.example.hlc06_enriquefernandez.modelo.Cat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatAdapter catAdapter;
    private List<Cat> catList = new ArrayList<>();
    private ICatApiService catApiService;
    private Button btnMoreCats;
    private Button btnFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnMoreCats = findViewById(R.id.btn_more_cats);
        btnFavourites = findViewById(R.id.btn_favourites);

        catApiService = ApiClient.getInstance().create(ICatApiService.class);

        catAdapter = new CatAdapter(catList, this, catApiService);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(catAdapter);

        loadMoreCats();

        btnMoreCats.setOnClickListener(v -> loadMoreCats());

        btnFavourites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });
    }

    private void loadMoreCats() {
        Call<List<Cat>> call = catApiService.getCats(2);
        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                if (response.isSuccessful()) {
                    catList.clear();
                    catList.addAll(response.body());
                    catAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, R.string.failed_to_load_cats, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed_to_load_cats, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
