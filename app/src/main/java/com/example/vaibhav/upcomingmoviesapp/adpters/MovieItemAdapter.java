package com.example.vaibhav.upcomingmoviesapp.adpters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vaibhav.upcomingmoviesapp.R;
import com.example.vaibhav.upcomingmoviesapp.models.MoviePojo;

import java.util.ArrayList;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {
    Context context;
    ArrayList<MoviePojo> list;
    OnClickListener mOnClickListener;

    public MovieItemAdapter(Context context, ArrayList<MoviePojo> list, OnClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final MoviePojo pos = list.get(i);
        viewHolder.tv_name.setText(pos.getTitle());
        viewHolder.tv_release_date.setText(pos.getRelease_date());
        viewHolder.tv_mode.setText(pos.isAdult()==true?"(A)":"(U/A)");

        viewHolder.tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onItemclick(view,i);
            }
        });
        Glide.with(context)
                .load(pos.getPoster_path())
                .into(viewHolder.img_icon);
    }

    @Override
    public int getItemCount() {
        return null!=list?list.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icon;
        TextView tv_name,tv_next,tv_release_date,tv_mode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_icon=itemView.findViewById(R.id.img_icon);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_next=itemView.findViewById(R.id.tv_next);
            tv_release_date=itemView.findViewById(R.id.tv_release_date);
            tv_mode=itemView.findViewById(R.id.tv_mode);
        }
    }

    public interface OnClickListener{
        void onItemclick(View view,int position);
    }
}
