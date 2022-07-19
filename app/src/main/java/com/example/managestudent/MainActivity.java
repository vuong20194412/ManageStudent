package com.example.managestudent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database database = new Database(this);
        SearchView searchView = findViewById(R.id.searchView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database.createTable();
        cursor = database.getListName();
        Adapter a = new Adapter(cursor);
        recyclerView.setAdapter(a);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a.cursor = database.getListName();
                a.notifyDataSetChanged();
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        Cursor cursor;
        public Adapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            cursor.moveToPosition(position);
            Log.d("TAG", "" + cursor.getString(0));
            ((TextView)holder.itemView).setText(cursor.getInt(0) + "\n" + cursor.getString(1));
            holder.itemView.setTag(cursor.getInt(0));
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ViewInfoActivity.class);
                intent.putExtra("0", (int)v.getTag());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    @Override
    protected void onDestroy() {
        database.closeDatabase();
        super.onDestroy();
    }
}