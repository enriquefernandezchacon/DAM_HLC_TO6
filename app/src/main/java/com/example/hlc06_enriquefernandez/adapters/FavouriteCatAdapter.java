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
import com.example.hlc06_enriquefernandez.modelo.FavouriteCat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FavouriteCatAdapter extends RecyclerView.Adapter<FavouriteCatAdapter.ViewHolder> {

    private final List<FavouriteCat> favouriteCatList;
    private final Context context;
    private final ICatApiService catApiService;

    public FavouriteCatAdapter(List<FavouriteCat> favouriteCatList, Context context, ICatApiService catApiService) {
        this.favouriteCatList = favouriteCatList;
        this.context = context;
        this.catApiService = catApiService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_cat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteCat favouriteCat = favouriteCatList.get(position);

        // Carga la imagen del gato favorito usando Glide
        Glide.with(context)
                .load(favouriteCat.getUrl())
                .into(holder.favouriteCatImage);

        // Maneja el clic en el botón "ELIMINAR FAV"
        holder.btnRemoveFav.setOnClickListener(v -> {
            Call<Void> call = catApiService.removeFromFavourites(favouriteCat.getId());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, R.string.success_delete_cat, Toast.LENGTH_SHORT).show();
                        favouriteCatList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, favouriteCatList.size());
                    } else {
                        Toast.makeText(context, R.string.error_delete_cat, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, R.string.error_delete_cat, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return favouriteCatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favouriteCatImage;
        Button btnRemoveFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteCatImage = itemView.findViewById(R.id.favourite_cat_image);
            btnRemoveFav = itemView.findViewById(R.id.btn_remove_fav);
        }
    }
}
