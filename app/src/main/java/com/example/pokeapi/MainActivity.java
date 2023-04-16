package com.example.pokeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView data;
    protected String url;
    protected String pokemonUrl;
    protected int listLength;
    protected JSONArray pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);
        url = "https://pokeapi.co/api/v2/generation/1";
        System.out.println(url);

        getPokemonList();
        getPokemonData();
    }

    public void setPokemonList(JSONArray pokemonList) {
        this.pokemonList = pokemonList;
        System.out.println("pokemonList2: " + pokemonList);
    }

    public void getPokemonList() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pokemonList = response.getJSONArray("pokemon_species");
                    setPokemonList(pokemonList);
                    //System.out.println(pokemonList);
                    //System.out.println(pokemonList.length());
                    //System.out.println(pokemonList.getJSONObject(0).getString("url"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    public void getPokemonData() {
        while (pokemonList == null) {
            for (int i = 0; i < pokemonList.length(); i++) {
                System.out.println("i: " + i);
                try {
                    pokemonUrl = pokemonList.getJSONObject(i).getString("url");
                    System.out.println("pokemonUrl: " + pokemonUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}