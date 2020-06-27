package com.example.mygithubapps.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mygithubapps.Activity.User;
import com.example.mygithubapps.Activity.UserDetail;
import com.example.mygithubapps.Adapter.FragmentAdapter;
import com.example.mygithubapps.Model.Follower;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;
import com.example.mygithubapps.ViewModel.FollowerViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {
    private ArrayList<Follower> list = new ArrayList<>();
    private FragmentAdapter adapter;
    private ProgressBar progressBar;
    public static String KEY_ACTIVITY = "msg_activity";


    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView rvFollower = view.findViewById(R.id.rv_follower);
        progressBar = view.findViewById(R.id.progressBar);


        String message = getArguments().getString(KEY_ACTIVITY);
        adapter = new FragmentAdapter(list);
        adapter.notifyDataSetChanged();

        rvFollower.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFollower.setAdapter(adapter);



        final FollowerViewModel follower = ViewModelProviders.of(this).get(FollowerViewModel.class);
        follower.getFollower().observe(getViewLifecycleOwner(), getFollower);
        follower.setFollower(message);
        showLoading(true);

        adapter.setOnItemClickCallback(new FragmentAdapter.OnItemClickCallback() {
            public void onItemClicked(Follower data) {
                Intent UserDetail = new Intent(getContext(), UserDetail.class);
                UserDetail.putExtra(com.example.mygithubapps.Activity.UserDetail.EXTRA_Data, data);
                startActivity(UserDetail);
            }
        });


    }

    private Observer<ArrayList<Follower>> getFollower = new Observer<ArrayList<Follower>>() {
        @Override
        public void onChanged(ArrayList<Follower> movieItems) {
            showLoading(true);

            if (movieItems != null) {
                adapter.setData(movieItems);
                showLoading(false);
            }
        }
    };





    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
