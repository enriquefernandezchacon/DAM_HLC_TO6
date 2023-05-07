package com.example.hlc06_enriquefernandez.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

        // Asigna el nombre del gato si está disponible
        if (cat.getName() != null) {
            holder.catName.setText(cat.getName());
        } else {
            holder.catName.setText(R.string.unknown_cat);
        }

        // Maneja el clic en el botón "FAV"
        holder.btnFav.setOnClickListener(v -> {
            // Aquí puedes implementar la lógica para añadir el gato a la lista de favoritos,
            // realizando una llamada a la API utilizando catApiService utilizando RetroFit y el catservice
            NewFavouriteCat newFavouriteCat = new NewFavouriteCat(cat.getId());
            Call<NewFavouriteCatResponse> call = catApiService.addToFavourites(newFavouriteCat);
            call.enqueue(new Callback<NewFavouriteCatResponse>() {
                @Override
                public void onResponse(Call<NewFavouriteCatResponse> call, retrofit2.Response<NewFavouriteCatResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cat added to favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error adding cat to favourites", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NewFavouriteCatResponse> call, Throwable t) {
                    Toast.makeText(context, "Error adding cat to favourites", Toast.LENGTH_SHORT).show();
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
        TextView catName;
        Button btnFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.cat_image);
            catName = itemView.findViewById(R.id.cat_name);
            btnFav = itemView.findViewById(R.id.btn_fav);
        }
    }
}
