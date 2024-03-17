package com.uyuanx.randomimages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RetrofitClient retrofitClient;
    RetrofitInterface retrofitInterface;

    List<Result> resultList = new ArrayList<>();
    RecyclerAdapter adapter;

    ResultList result;
    int page = 1;

    String searchStr;

    //<a target="_blank" href="https://icons8.com/icon/86327/download">Download</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    //<a target="_blank" href="https://icons8.com/icon/85154/instagram">Instagram</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    //<a target="_blank" href="https://icons8.com/icon/82751/user">User</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>


    private static final String API_KEY = "";
    //private String API_LINK = "https://api.unsplash.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ri_listener", "Start");

        RecyclerView imageRecycler = findViewById(R.id.recycler_list);
        adapter = new RecyclerAdapter(this, resultList);
        imageRecycler.setAdapter(adapter);
        imageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        EditText searchTxt = findViewById(R.id.search);
        Button searchBtn = findViewById(R.id.btn);

        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = retrofitClient.getRetrofitInterface();

        result = null;

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ri_listener", "Search");
                searchStr = searchTxt.getText().toString();
                page = 1;
                resultList.clear();
                getImages(searchStr, page);
            }
        });
        imageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !resultList.isEmpty()) {
                    page++;
                    getImages(searchStr,page);
                }
            }
        });

    }

    public void getImages(String search, int page) {
        retrofitInterface.getRandomImage(API_KEY, search, 12, page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                result = response.body();
                ArrayList resultsList = result.getResults();
                if (resultsList != null) {
                    for(Object i : resultsList){
                        resultList.add((Result)i);
                    }
                }
                Log.d("ri_retrofit", "Data fetch success");
                adapter.setData(resultList);


            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.d("ri_retrofit", t.getMessage());
            }
        });

    }
}