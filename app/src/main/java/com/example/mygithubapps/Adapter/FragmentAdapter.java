package com.example.mygithubapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mygithubapps.Model.Follower;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;

import java.util.ArrayList;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder>{
    private ArrayList<Follower> UserFragment;
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<Follower> items) {
        UserFragment.clear();
        UserFragment.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public FragmentAdapter(ArrayList<Follower> listUser)
    {
        this.UserFragment = listUser;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new FragmentAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Follower user = UserFragment.get(position);
        holder.nama.setText(user.getLogin());

        holder.type.setText(user.getType());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar_url())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(UserFragment.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return UserFragment.size();
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

    public interface OnItemClickCallback {
        void onItemClicked(Follower data);
    }
}
