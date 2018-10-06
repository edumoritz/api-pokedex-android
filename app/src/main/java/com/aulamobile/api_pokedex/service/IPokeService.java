package com.aulamobile.api_pokedex.service;

import com.aulamobile.api_pokedex.models.PokemonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPokeService {

    @GET("pokemon")
    Call<PokemonRequest> listPokes(@Query("limit") int limit, @Query("offset") int offset);

}
