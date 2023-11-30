package com.example.asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.MainActivity2;
import com.example.asm.Model.Comic;
import com.example.asm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicAdapter2 extends RecyclerView.Adapter<ComicAdapter2.ViewHolder> {

    public List<Comic> getComicList() {
        return comicList;
    }


    List<Comic> comicList;

    private Context context;

    public ComicAdapter2(List<Comic> comicList, Context context) {
        this.comicList = comicList;
        this.context = context;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private MainActivity2 mainActivity2;



    public void setMainActivity2(MainActivity2 mainActivity2) {
        this.mainActivity2 = mainActivity2;
    }




    public ComicAdapter2(List<Comic> comicList) {
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comic comic=comicList.get(position);
        holder.titleTextView.setText(comic.getTitle());
        holder.tvname.setText(String.valueOf(comic.getName()));
        holder.tvchapter.setText(comic.getChapter());






        try {
            Picasso.get().load(comic.getImage()).into(holder.thumbnailImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvname,tvchapter;
        public TextView titleTextView;
        public ImageView thumbnailImageView;
        public ImageView imgDelete,imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView= itemView.findViewById(R.id.tvtitle);
            thumbnailImageView=itemView.findViewById(R.id.imageV5);
            tvname=itemView.findViewById(R.id.tvname);
            tvchapter=itemView.findViewById(R.id.tvchapter);
            imgDelete=itemView.findViewById(R.id.imgDeleteS);
            imgEdit=itemView.findViewById(R.id.imgEdit);
        }
    }



}
