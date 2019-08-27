package com.ta.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<Hero>> call = api.getHeros();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {

                List<Hero> heroes = response.body();
                String [] heronames = new String [heroes.size()];
                for(int i=0; i<heroes.size(); i++){

                    heronames[i] = heroes.get(i).getName();
                }
                listView.setAdapter(new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,heronames
                ));


            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Log"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
