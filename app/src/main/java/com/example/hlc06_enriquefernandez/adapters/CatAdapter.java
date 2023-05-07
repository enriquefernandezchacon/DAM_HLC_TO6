package com.example.hlc06_enriquefernandez.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hlc06_enriquefernandez.R;
import com.example.hlc06_enriquefernandez.apiservice.ICatApiService;
import com.example.hlc06_enriquefernandez.modelo.Cat;
import com.example.hlc06_enriquefernandez.modelo.NewFavouriteCat;
import com.example.hlc06_enriquefernandez.modelo.NewFavouriteCatResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private final List<Cat> catList;
    private final Context context;
    private final ICatApiService catApiService;

    public CatAdapter(List<Cat> catList, Context context, ICatApiService catApiService) {
        this.catList = catList;
        this.context = context;
        this.catApiService = catApiService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cat cat = catList.get(position);

        // Carga la imagen del gato usando Glide
        Glide.with(context)
                .load(cat.getUrl())
                .into(holder.catImage);

        // Maneja el clic en el botón "AÑADIR A FAVORITOS"
        holder.btnFav.setOnClickListener(v -> {
            // Nuevo objeto NewFavouriteCat con el id del gato que cumple el modelo JSON esperado por la API
            NewFavouriteCat newFavouriteCat = new NewFavouriteCat(cat.getId());
            // Llamada a la API para añadir el gato a favoritos
            Call<NewFavouriteCatResponse> call = catApiService.addToFavourites(newFavouriteCat);
            // Callback para manejar la respuesta de la API
            call.enqueue(new Callback<NewFavouriteCatResponse>() {
                //Respuesta del servidor
                @Override
                public void onResponse(Call<NewFavouriteCatResponse> call, retrofit2.Response<NewFavouriteCatResponse> response) {
                    // Si la respuesta es correcta, muestra un mensaje de éxito
                    if (response.isSuccessful()) {
                        Toast.makeText(context, R.string.success_add_cat, Toast.LENGTH_SHORT).show();
                    // Si la respuesta es incorrecta, muestra un mensaje de error
                    } else {
                        Toast.makeText(context, R.string.error_add_cat, Toast.LENGTH_SHORT).show();
                    }
                }
                // Error en la llamada a la API
                @Override
                public void onFailure(Call<NewFavouriteCatResponse> call, Throwable t) {
                    Toast.makeText(context, R.string.error_add_cat, Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImage;
        Button btnFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.cat_image);
            btnFav = itemView.findViewById(R.id.btn_fav);
        }
    }
}
