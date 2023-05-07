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
        //Cargamos el layout de la actividad
        setContentView(R.layout.activity_favourites);
        //Obtenemos la referencia al RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFavourites);
        //Obtenemos la referencia al servicio de la API
        catApiService = ApiClient.getInstance().create(ICatApiService.class);
        //Creamos el adaptador y lo asignamos al RecyclerView
        favouriteCatAdapter = new FavouriteCatAdapter(favouriteCatList, this, catApiService);
        //Asignamos el LayoutManager al RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Asignamos el adaptador al RecyclerView
        recyclerView.setAdapter(favouriteCatAdapter);
        //Cargamos los gatos favoritos
        loadFavouriteCats();
    }

    private void loadFavouriteCats() {
        //Preparamos la llamada a la API
        Call<List<FavouriteCat>> call = catApiService.getFavouriteCats();
        //Realizamos la llamada a la API
        call.enqueue(new Callback<List<FavouriteCat>>() {
            //En caso de que la llamada sea correcta
            @Override
            public void onResponse(Call<List<FavouriteCat>> call, Response<List<FavouriteCat>> response) {
                //Si la respuesta es correcta
                if (response.isSuccessful()) {
                    //Limpiamos la lista de gatos favoritos
                    favouriteCatList.clear();
                    //Añadimos los gatos favoritos a la lista
                    favouriteCatList.addAll(response.body());
                    //Notificamos al adaptador que los datos han cambiado
                    favouriteCatAdapter.notifyDataSetChanged();
                } else {
                    //Si la respuesta no es correcta mostramos un mensaje de error
                    Toast.makeText(FavouritesActivity.this, R.string.failed_to_load_favourites, Toast.LENGTH_SHORT).show();
                }
            }

            //En caso de que la llamada no sea correcta
            @Override
            public void onFailure(Call<List<FavouriteCat>> call, Throwable t) {
                Toast.makeText(FavouritesActivity.this, R.string.failed_to_load_favourites, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
