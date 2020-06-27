package com.example.consumerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>  {
    private ArrayList<FavoriteUser> mUser = new ArrayList<>();
    private final Activity activity;


    public UserFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteUser> getFavoriteUser() {
        return mUser;
    }

    public void setFavoriteUser(ArrayList<FavoriteUser> favoriteUser) {
        this.mUser.clear();
        this.mUser.addAll(favoriteUser);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserFavoriteAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nama.setText(getFavoriteUser().get(position).getLogin());

        holder.type.setText(getFavoriteUser().get(position).getType());
        Glide.with(holder.itemView.getContext())
                .load(getFavoriteUser().get(position).getAvatar_url())
                .into(holder.photo);


    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama,type;
        ImageView photo;
        public View lyt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txt_nama_user);
            type = itemView.findViewById(R.id.txt_type_user);
            photo = itemView.findViewById(R.id.img_photo_user);
            lyt_parent = itemView.findViewById(R.id.layout_user);
        }
    }
}
