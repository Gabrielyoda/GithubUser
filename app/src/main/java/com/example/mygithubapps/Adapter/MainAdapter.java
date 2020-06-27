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
import com.example.mygithubapps.Activity.User;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private ArrayList<Item> mUser = new ArrayList<>();
    private Context ctx;

    public void setData(ArrayList<Item> items) {
        mUser.clear();
        mUser.addAll(items);
        notifyDataSetChanged();
    }

    public MainAdapter(Context ctx)
    {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item user = mUser.get(position);
        holder.nama.setText(user.getLogin());

        holder.type.setText(user.getType());
        Glide.with(ctx)
                .load(user.getAvatar_url())
                .into(holder.photo);

        final Item item = new Item();
        item.setLogin(user.getLogin());
        item.setId(user.getId());
        item.setAvatar_url(user.getAvatar_url());
        item.setType(user.getType());

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, User.class);
                intent.putExtra(User.EXTRA_USER, item);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
