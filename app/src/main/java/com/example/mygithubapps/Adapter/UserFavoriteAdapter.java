package com.example.mygithubapps.Adapter;

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
import com.example.mygithubapps.Activity.Favorite;
import com.example.mygithubapps.Activity.User;
import com.example.mygithubapps.Activity.UserDetail;
import com.example.mygithubapps.Activity.UserDetailFavorite;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;

import java.util.ArrayList;
import java.util.List;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>  {
    private List<FavoriteUser> mUser;
    private Context ctx;


    public UserFavoriteAdapter(List<FavoriteUser> favoriteUsers, Context context) {
        this.mUser = favoriteUsers;
        this.ctx = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserFavoriteAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteUser user = mUser.get(position);
        holder.nama.setText(user.getLogin());

        holder.type.setText(user.getType());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar_url())
                .into(holder.photo);

        final FavoriteUser item = new FavoriteUser();
        item.setLogin(user.getLogin());
        item.setId(user.getId());
        item.setAvatar_url(user.getAvatar_url());
        item.setType(user.getType());
        item.setFollowing(user.getFollowing());
        item.setFollowers(user.getFollowers());

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, UserDetailFavorite.class);
                intent.putExtra(UserDetailFavorite.EXTRA_Data, item);
                ctx.startActivity(intent);
            }
        });
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
