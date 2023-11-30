package com.example.asm;

import static com.example.asm.RetrofitRequest.getRetrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asm.Adapter.ComicAdapter;
import com.example.asm.Adapter.ComicAdapter2;
import com.example.asm.Interface.ComicService;
import com.example.asm.Model.Comic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComicAdapter2 comicAdapter;
    private ComicService comicService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        recyclerView=findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        capnhatRCV();
        loadData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Xử lý khi item bị vuốt qua (ví dụ: hiển thị thông tin)
                int position = viewHolder.getAdapterPosition();
                Comic comic = comicAdapter.getComicList().get(position);
                showComicDetail(comic);
             
                loadData();

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    private void showComicDetail(Comic comic) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết Comic");

        // Hiển thị thông tin của comic trong AlertDialog
        String comicDetail = "Tên: " + comic.getName() + "\n" +
                "Tác giả: " + comic.getTitle() + "\n" +
                "Mô tả: " + comic.getChapter();

        builder.setMessage(comicDetail);

        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void capnhatRCV(){
        List<Comic> comicList=new ArrayList<>();
        comicAdapter =new ComicAdapter2(comicList);
        comicAdapter.setMainActivity2(this);
        recyclerView.setAdapter(comicAdapter);
    }

    public void loadData(){
        comicService=getRetrofit().create(ComicService.class);
        Call<List<Comic>> call=comicService.getComic();

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                List<Comic> comicList=null;

                if (response.isSuccessful() && response.body() != null){
                    comicList=response.body();
                    comicAdapter.setComicList(comicList);
                    Log.e("e", "Photo...." + comicList);
                    comicAdapter.notifyDataSetChanged();
                }else {
                    Log.e("e", "Photo...." + comicList);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });

    }




}