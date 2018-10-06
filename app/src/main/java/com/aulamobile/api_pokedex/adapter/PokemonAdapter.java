package com.aulamobile.api_pokedex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aulamobile.api_pokedex.R;
import com.aulamobile.api_pokedex.models.Pokemon;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> mList;
    private Context context;

    public PokemonAdapter(Context context){
        this.context = context;
        mList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = mList.get(position);

        holder.name.setText(p.getName());

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addLista(ArrayList<Pokemon> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.id_img);
            name = itemView.findViewById(R.id.id_name);
        }
    }
}
