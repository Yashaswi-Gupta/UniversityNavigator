package com.example.university;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UniversityListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UniversityData> universityList;
    private ProgressDialog dialog;
    private EditText searchBar;
    private UniversityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list);

        recyclerView = findViewById(R.id.universities);
        universityList = new ArrayList<>();

        searchBar = findViewById(R.id.searchBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new UniversityAdapter(this, universityList);
        recyclerView.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        ApiUtilities.getUniversityApi().getUniversityData().enqueue(new Callback<List<UniversityData>>() {
            @Override
            public void onResponse(Call<List<UniversityData>> call, Response<List<UniversityData>> response) {
                universityList.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<UniversityData>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(UniversityListActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter (String text){
        List<UniversityData> filterList = new ArrayList<>();
        for (UniversityData items : universityList){
            if(items.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(items);
            }
        }
        adapter.filterList(filterList);
    }
}