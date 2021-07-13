package com.example.voicerecording;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {
    Context context;
    List<File>fileList;
    private OnSelectListener listener;

    public RecAdapter(Context context, List<File> fileList,OnSelectListener listener) {
        this.context = context;
        this.fileList = fileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {
        holder.tvName.setText(fileList.get(position).getName());
        holder.tvName.setSelected(true);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(fileList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
