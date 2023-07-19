package com.example.photomanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
   private Context context;
   private ArrayList<String> imagepatharrayList;

    public RecyclerViewAdapter(Context context, ArrayList<String> imagepatharrayList) {
        this.context = context;
        this.imagepatharrayList = imagepatharrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        File imgfile=new File(imagepatharrayList.get(position));
        if(imgfile.exists())
        {
            Picasso.get().load(imgfile).placeholder(R.drawable.ic_launcher_background).into(holder.imageIV);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,ImageDetailActivity.class);
                    intent.putExtra("imgpath", imagepatharrayList.get(position));
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return imagepatharrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageIV;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageIV=itemView.findViewById(R.id.idIVImage);
        }
    }
}
