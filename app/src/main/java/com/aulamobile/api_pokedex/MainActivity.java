package com.aulamobile.api_pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.aulamobile.api_pokedex.adapter.PokemonAdapter;
import com.aulamobile.api_pokedex.models.Pokemon;
import com.aulamobile.api_pokedex.models.PokemonRequest;
import com.aulamobile.api_pokedex.service.IPokeService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    private int offset;

    private boolean carregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rvList);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (carregar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Chegamos ao final.");

                            carregar = false;
                            offset += 20;
                            obterDados(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        carregar = true;
        offset = 0;
        obterDados(offset);
    }

    private void obterDados(int offset) {
        IPokeService service = retrofit.create(IPokeService.class);
        Call<PokemonRequest> requestCall = service.listPokes(20, offset);

        requestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                carregar = true;
                if (response.isSuccessful()) {

                    PokemonRequest request = response.body();
                    ArrayList<Pokemon> listaPokemon = request.getResults();

                    adapter.addLista(listaPokemon);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                carregar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}
