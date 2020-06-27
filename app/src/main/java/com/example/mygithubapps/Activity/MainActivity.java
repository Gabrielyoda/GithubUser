package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mygithubapps.Adapter.MainAdapter;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;
import com.example.mygithubapps.ViewModel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainAdapter adapter;
    private EditText edtCari;
    private ProgressBar progressBar;
    private Button btnCari;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCari = findViewById(R.id.edtCari);
        progressBar = findViewById(R.id.progressBar);
        btnCari = findViewById(R.id.btnCari);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtCari.getText().toString();

                if (TextUtils.isEmpty(username)) return;

                mainViewModel.setUser(username);
                showLoading(true);
            }
        });

        mainViewModel.getUser().observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> Items) {
                if (Items != null) {
                    adapter.setData(Items);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
