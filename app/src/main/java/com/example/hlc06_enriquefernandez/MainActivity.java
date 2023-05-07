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

    private static final int CATS_LIMIT = 2;
    private CatAdapter catAdapter;
    private final List<Cat> catList = new ArrayList<>();
    private ICatApiService catApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establecemos el layout de la actividad
        setContentView(R.layout.activity_main);
        //Obtenemos las referencias a los elementos de la interfaz
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button btnMoreCats = findViewById(R.id.btn_more_cats);
        Button btnFavourites = findViewById(R.id.btn_favourites);
        //Obtenemoun una instancia de la api de gatos combinndo la interfaz con retorfit
        catApiService = ApiClient.getInstance().create(ICatApiService.class);
        //Creamos el adaptador y lo asignamos al recycler view
        catAdapter = new CatAdapter(catList, this, catApiService);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(catAdapter);
        //Cargamos los gatos
        loadMoreCats();
        //Asignamos la funcion al boton de cargar mas gatos
        btnMoreCats.setOnClickListener(v -> loadMoreCats());
        //Asignamos la funcion al boton de ver favoritos
        btnFavourites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });
    }

    private void loadMoreCats() {
        //Preparamos la llamada a la api
        Call<List<Cat>> call = catApiService.getCats(CATS_LIMIT);
        //Realizamos la llamada a la api
        call.enqueue(new Callback<List<Cat>>() {
            //Si la llamada es correcta
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                //Si la respuesta es correcta
                if (response.isSuccessful()) {
                    //Limpiamos la lista de gatos y añadimos los nuevos

                    catList.clear();
                    for (Cat cat : response.body()) {
                        Call<Cat> callSingleCat = catApiService.getCatById(cat.getId());
                        callSingleCat.enqueue(new Callback<Cat>() {
                            @Override
                            public void onResponse(Call<Cat> call, Response<Cat> response) {
                                if (response.isSuccessful()) {
                                    catList.add(response.body());
                                    catAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.failed_to_load_single_cats, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cat> call, Throwable t) {
                                Toast.makeText(MainActivity.this, R.string.failed_to_load_cats, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //catList.addAll(response.body());
                    //Notificamos al adaptador que los datos han cambiado
                    catAdapter.notifyDataSetChanged();
                } else {
                    //Si la respuesta no es correcta mostramos un mensaje de error
                    Toast.makeText(MainActivity.this, R.string.failed_to_load_cats, Toast.LENGTH_SHORT).show();
                }
            }

            //Si la llamada falla mostramos un mensaje de error
            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed_to_load_cats, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
